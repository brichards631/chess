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
        Collection<ChessMove> validMoves = new ArrayList<>();

        if (type == PieceType.PAWN) {
            addPawnMoves(validMoves, board, myPosition); //will implement addPawnMoves below
        } else if (type == PieceType.ROOK) {
            addRookMoves(validMoves, board, myPosition);
        } else if (type == PieceType.KNIGHT) {
            addKnightMoves(validMoves, board, myPosition);
        } else if (type == PieceType.BISHOP) {
            addBishopMoves(validMoves, board, myPosition);
        }
//        } else if (type == PieceType.QUEEN) {
//            addQueenMoves(validMoves, board, myPosition);
//        } else if (type == PieceType.KING) {
//            addKingMoves(validMoves, board, myPosition);
//        }
        return validMoves;
    }

    private void addPawnMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition) {
        int startRow = myPosition.getRow();
        int startCol = myPosition.getColumn();

        boolean isWhite = pieceColor == ChessGame.TeamColor.WHITE;

        int direction = isWhite ? 1 : -1; //white pawns move up 1, black pawns move down 1
        int startingRow = isWhite ? 2 : 7;
        int promotionRow = isWhite ? 8: 1; //white promotes at row 8, black at 1

        ChessPosition oneStepForward = new ChessPosition(startRow + direction, startCol);
        if (board.getPiece(oneStepForward) == null) {
            if (oneStepForward.getRow() == promotionRow){
                addPromotionMoves(validMoves, myPosition, oneStepForward);
            } else {
                validMoves.add(new ChessMove(myPosition, oneStepForward, null));
            }
        }

        // two spaces forward if starting
        if (startRow == startingRow) {
            ChessPosition twoStepsForward = new ChessPosition(startRow + 2 * direction, startCol);
            ChessPosition oneStepIntermediate = new ChessPosition(startRow + direction, startCol);

            if (board.getPiece(oneStepIntermediate) == null && board.getPiece(twoStepsForward) == null) {
                validMoves.add(new ChessMove(myPosition, twoStepsForward, null));
            }
        }

        // capturing diagonally
        ChessPosition leftDiagonal = new ChessPosition(startRow + direction, startCol - 1);
        ChessPosition rightDiagonal = new ChessPosition(startRow + direction, startCol + 1);

        if (startCol > 1 && board.getPiece(leftDiagonal) != null && board.getPiece(leftDiagonal).getTeamColor() != pieceColor) {
            if (leftDiagonal.getRow() == promotionRow) {
                addPromotionMoves(validMoves, myPosition, leftDiagonal);
            } else {
                validMoves.add(new ChessMove(myPosition, leftDiagonal, null));
            }
        }
        if (startCol < 8 && board.getPiece(rightDiagonal) != null && board.getPiece(rightDiagonal).getTeamColor() != pieceColor) {
            if (rightDiagonal.getRow() == promotionRow) {
                addPromotionMoves(validMoves, myPosition, rightDiagonal);
            } else {
                validMoves.add(new ChessMove(myPosition, rightDiagonal, null));
            }
        }
    }

    private void addRookMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition) {
        int startRow = myPosition.getRow();
        int startCol = myPosition.getColumn();

        //horizontal movement
        for (int col = startCol - 1; col >= 1; col--) {
            ChessPosition newPosition = new ChessPosition(startRow, col);
            if (!addMoveOrStop(validMoves, board, myPosition, newPosition)) break;
        }
        for (int col = startCol + 1; col <= 8; col++) {
            ChessPosition newPosition = new ChessPosition(startRow, col);
            if (!addMoveOrStop(validMoves, board, myPosition, newPosition)) break;
        }

        //vertical movement
        for (int row = startRow - 1; row >= 1; row--) {
            ChessPosition newPosition = new ChessPosition(row, startCol);
            if (!addMoveOrStop(validMoves, board, myPosition, newPosition)) break;
        }
        for (int row = startRow + 1; row <= 8; row++) {
            ChessPosition newPosition = new ChessPosition(row, startCol);
            if (!addMoveOrStop(validMoves, board, myPosition, newPosition)) break;
        }
    }

    private void addKnightMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition) {
        int startRow = myPosition.getRow();
        int startCol = myPosition.getColumn();

        int[][] knightMoves = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        for (int[] move : knightMoves) {
            int newRow = startRow + move[0];
            int newCol = startCol + move[1];

            if (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8) {
                ChessPosition newPosition = new ChessPosition(newRow, newCol);
                ChessPiece pieceAtDestination = board.getPiece(newPosition);

                if (pieceAtDestination == null || pieceAtDestination.getTeamColor() != this.pieceColor) {
                    validMoves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }
    }

    private void addBishopMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition myPosition) {
        int startRow = myPosition.getRow();
        int startCol = myPosition.getColumn();

        addDiagonalMoves(validMoves, board, myPosition, 1, 1);
        addDiagonalMoves(validMoves, board, myPosition, 1, -1);
        addDiagonalMoves(validMoves, board, myPosition, -1, 1);
        addDiagonalMoves(validMoves, board, myPosition, -1, -1);
    }

    private void addPromotionMoves(Collection<ChessMove> validMoves, ChessPosition start, ChessPosition end) {
        validMoves.add(new ChessMove(start, end, ChessPiece.PieceType.QUEEN));
        validMoves.add(new ChessMove(start, end, ChessPiece.PieceType.ROOK));
        validMoves.add(new ChessMove(start, end, ChessPiece.PieceType.BISHOP));
        validMoves.add(new ChessMove(start, end, ChessPiece.PieceType.KNIGHT));
    }

    private boolean addMoveOrStop(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition from, ChessPosition to) {
        ChessPiece pieceAtDestination = board.getPiece(to);

        if (pieceAtDestination == null) {
            validMoves.add(new ChessMove(from, to, null));
            return true;
        }

        if (pieceAtDestination.getTeamColor() != this.pieceColor) {
            validMoves.add(new ChessMove(from, to, null));
        }

        return false;
    }

    private void addDiagonalMoves(Collection<ChessMove> validMoves, ChessBoard board, ChessPosition startPosition, int rowIncrement, int colIncrement) {
        int currentRow = startPosition.getRow();
        int currentCol = startPosition.getColumn();

        while (true) {
            currentRow += rowIncrement;
            currentCol += colIncrement;

            if (currentRow < 1 || currentRow > 8 || currentCol < 1 || currentCol > 8) {
                break;
            }

            ChessPosition newPosition = new ChessPosition(currentRow, currentCol);
            ChessPiece pieceAtDestination = board.getPiece(newPosition);

            if (pieceAtDestination == null) {
                validMoves.add(new ChessMove(startPosition, newPosition, null));
            } else {
                if (pieceAtDestination.getTeamColor() != this.pieceColor) {
                    validMoves.add(new ChessMove(startPosition, newPosition, null));
                }
                break;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same reference
        if (o == null || getClass() != o.getClass()) return false; // Null or different class

        ChessPiece that = (ChessPiece) o;

        // Compare both the piece type and color
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        int result = pieceColor.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
