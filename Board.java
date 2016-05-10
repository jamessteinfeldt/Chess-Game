import java.util.List;

public class Board {
	private Piece[][] board = new Piece[8][8];
	private static boolean calledFromWouldPutInCheck = false;

	// ------------------------------------------------------------------------
	// Setup
	// ------------------------------------------------------------------------

	/**
	 * Removes all pieces from the board.
	 */
	public void clear() {
		for (int rank = 0; rank < 8; rank++) {
			for (int file = 0; file < 8; file++) {
				board[rank][file] = null;
			}
		}
	}

	/**
	 * Sets the back two ranks of each player to be the starting position. The
	 * back rank gets the sequence <R, K, B, Q, K, B, K, N>, and the second to
	 * last rank gets eight pawns.
	 */
	public void initialize() {
		clear();

		for (int file = 0; file < 8; file++) {
			board[1][file] = new Pawn(this, Player.BLACK);
			board[6][file] = new Pawn(this, Player.WHITE);
		}

		board[0][0] = new Rook(this, Player.BLACK);
		board[0][1] = new Knight(this, Player.BLACK);
		board[0][2] = new Bishop(this, Player.BLACK);
		board[0][3] = new Queen(this, Player.BLACK);
		board[0][4] = new King(this, Player.BLACK);
		board[0][5] = new Bishop(this, Player.BLACK);
		board[0][6] = new Knight(this, Player.BLACK);
		board[0][7] = new Rook(this, Player.BLACK);

		board[7][0] = new Rook(this, Player.WHITE);
		board[7][1] = new Knight(this, Player.WHITE);
		board[7][2] = new Bishop(this, Player.WHITE);
		board[7][3] = new Queen(this, Player.WHITE);
		board[7][4] = new King(this, Player.WHITE);
		board[7][5] = new Bishop(this, Player.WHITE);
		board[7][6] = new Knight(this, Player.WHITE);
		board[7][7] = new Rook(this, Player.WHITE);
	}

	// ------------------------------------------------------------------------
	// Position
	// ------------------------------------------------------------------------

	/**
	 * Determines if a tile is occupied.
	 *
	 * @param t The tile.
	 * @return true if the tile is occupied, false otherwise.
	 */
	public boolean isOccupied(Tile t) {
		return getPieceAt(t) != null;
	}

	/**
	 * Determines if a tile is occupied by a particular player.
	 *
	 * @param t      The tile.
	 * @param player The player.
	 * @return true if the tile is occupied by player, false otherwise.
	 */
	public boolean isOccupiedByPlayer(Tile t, Player player) {
		return getPieceAt(t) != null && getPieceAt(t).getPlayer() == player;
	}

	/**
	 * Determines if given player's king piece is threatened
	 * 
	 * @param player 	The given player
	 * @return true if player's king is threatened (an opponent piece could move to take it)
	 */

	public boolean isPLayerinCheck(Player player){
		for (int rank = 0; rank < 8; rank++) {
			for (int file = 0; file < 8; file++) {
				Tile t = new Tile(rank,file);
				Piece p = this.getPieceAt(t);
				
				if(isOccupiedByPlayer(t, player.opposite())){
					for(Tile tile : getPieceAt(t).getAllMoves(t)){
						if(isOccupiedByPlayer(tile, player)){
	                		   if(getPieceAt(tile).isKing() ){
	                			     return true;
	                		   }
	                		}
					}
				}
				
				

			}
		}
	
	


		return false;
	}

	/**
	 * Returns true if the given Player cannot make a move to leave check
	 * 
	 * @param player		The given Player
	 * @return true if		player has no possible moves to leave check
	 */

	public boolean isPlayerInCheckMate(Player player){
		for (int rank = 0; rank < 8; rank++){
			for (int file = 0; file < 8; file++){
				Tile curTile = new Tile(rank, file);
				Piece curPiece = this.getPieceAt(curTile);
				if(isOccupiedByPlayer(curTile, player)){
					List <Tile> safeMoves = curPiece.getAllSafeMoves(curTile);
					if (!safeMoves.isEmpty()){
						return false;
					}
				}
			}
		}

		return true;
		}


	/**
	 * Retrieves the piece at the given tile.
	 *
	 * @param t The tile.
	 * @return The piece at the given tile, or null if the tile is unoccupied.
	 */
	public Piece getPieceAt(Tile t) {
		if (!t.isValid()) {
			return null;
		}

		return board[t.getRank()][t.getFile()];
	}

	/**
	 * Updates the piece at the given tile.
	 *
	 * @param t     The tile.
	 * @param piece The piece.
	 * @throws RuntimeException If the tile is not valid.
	 */
	public void setPieceAt(Tile t, Piece piece) {
		if (!t.isValid()) {
			throw new RuntimeException("Tile is not valid.");
		}

		board[t.getRank()][t.getFile()] = piece;
	}

	/**
	 * Determines if the piece at the from tile can move to the to tile without 
	 * putting the player in check
	 * 
	 * @param from		The current tile
	 * @param to		The destination tile
	 * @return true if	The move would result in check
	 */

	public boolean wouldPutInCheck(Tile from, Tile to) {
		// Backup and initialize variables
		Tile origTo = to;
		Tile origFrom = from;
		Piece toPiece = this.getPieceAt(to);
		Piece fromPiece = this.getPieceAt(from);
		Player player = fromPiece.getPlayer();
		boolean isInCheck = false;



		// Actually move piece 
		setPieceAt(to, fromPiece);
		setPieceAt(from, null);


		// Check to see if this moves puts player in check
		if (this.isPLayerinCheck(player)){
			isInCheck = true;
		}

		// Restore board state
		setPieceAt(origTo, toPiece);
		setPieceAt(origFrom, fromPiece);

		// Return boolean
		return isInCheck;
	}

	// ------------------------------------------------------------------------
	// Movement
	// ------------------------------------------------------------------------

	/**
	 * Attempts to move a piece from the source tile to the destination tile. This method
	 * may fail for the following reasons:
	 * <p>
	 * <ul>
	 * <li>The source or destination tile refers outside the board.</li>
	 * <li>The source tile is unoccupied.</li>
	 * <li>The piece at the source tile cannot move or capture the destination tile.</li>
	 * </ul>
	 *
	 * @param from The source tile.
	 * @param to   The destination tile.
	 * @return true if movement succeeded, false otherwise
	 */
	public boolean move(Tile from, Tile to) {



		if (!from.isValid() || !to.isValid() || from.equals(to) || !isOccupied(from) || wouldPutInCheck(from,to)) {
			return false;
		}

		Piece piece = getPieceAt(from);

		if (!piece.canMove(from, to)) {
			return false;
		}

		setPieceAt(to, piece);
		setPieceAt(from, null);
		calledFromWouldPutInCheck = false;
		return true;
	}




}
