package chess;

import java.util.Collection;
import java.util.ArrayList;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor pieceColor;
    private PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
        //throw new RuntimeException("Not implemented");
    }

    public String getSymbol() {
        switch (type) {
            case KING:
                return pieceColor == ChessGame.TeamColor.BLACK ? "k" : "K";
            case QUEEN:
                return pieceColor == ChessGame.TeamColor.BLACK ? "q" : "Q";
            case BISHOP:
                return pieceColor == ChessGame.TeamColor.BLACK ? "b" : "B";
            case KNIGHT:
                return pieceColor == ChessGame.TeamColor.BLACK ? "n" : "N";
            case ROOK:
                return pieceColor == ChessGame.TeamColor.BLACK ? "r" : "R";
            case PAWN:
                return pieceColor == ChessGame.TeamColor.BLACK ? "p" : "P";
            default:
                throw new IllegalArgumentException("Unknown piece type");
        }
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return new ArrayList<>();
        //throw new RuntimeException("Not implemented");
    }
}
