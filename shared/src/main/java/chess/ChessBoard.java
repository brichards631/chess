package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece[][] board;

    public ChessBoard() {
        // Initialize board array
        board = new ChessPiece[8][8];
    }


    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = 8 - position.getRow();
        int col = position.getColumn() - 1;
        board[row][col] = piece;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = 8 - position.getRow();
        int col = position.getColumn() - 1;
        return board[row][col];
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // Set the default chess positions for black pieces (top of the board)
        board[0] = new ChessPiece[]{ChessPiece.ROOK, ChessPiece.KNIGHT, ChessPiece.BISHOP, ChessPiece.QUEEN, ChessPiece.KING, ChessPiece.BISHOP, ChessPiece.KNIGHT, ChessPiece.ROOK};
        board[1] = new ChessPiece[]{ChessPiece.PAWN, ChessPiece.PAWN, ChessPiece.PAWN, ChessPiece.PAWN, ChessPiece.PAWN, ChessPiece.PAWN, ChessPiece.PAWN, ChessPiece.PAWN};

        // Set the default chess positions for white pieces (bottom of the board)
        board[7] = new ChessPiece[]{ChessPiece.ROOK, ChessPiece.KNIGHT, ChessPiece.BISHOP, ChessPiece.QUEEN, ChessPiece.KING, ChessPiece.BISHOP, ChessPiece.KNIGHT, ChessPiece.ROOK};
        board[6] = new ChessPiece[]{ChessPiece.PAWN, ChessPiece.PAWN, ChessPiece.PAWN, ChessPiece.PAWN, ChessPiece.PAWN, ChessPiece.PAWN, ChessPiece.PAWN, ChessPiece.PAWN};

        // Clear the rest of the board (empty positions in the middle)
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
        //throw new RuntimeException("Not implemented");
    }
}
