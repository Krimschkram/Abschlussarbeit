package Client;

public class PatternScanner     {

    static int[][][] patterns = new int[][][]{
            {{0, 0}, {0, 1}, {0, 2}, {0, 3}},
            {{0, 0}, {1, 0}, {2, 0}, {3, 0}},
            {{0, 0}, {1, 1}, {2, 2}, {3, 3}},
            {{3, 0}, {2, 1}, {1, 2}, {0, 3}},
    };

    public static char scan(char[][] feld) { //'0' == leer, 'B' == blau, 'R' == rot
        int min = Integer.MAX_VALUE;
        for (int[][] pattern : patterns) {
            min = Math.min(min, patternScan(pattern, feld));
        }

        if (min == 'l'){
            return '0';
        }
        return (char) min;
    }

    public static char patternScan(int[][] pattern, char[][] feld) {
        boolean okay;
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                okay = true;
                for (int i = 1; i < pattern.length; i++) {
                    try {
                        if (feld[y + pattern[i - 1][0]][x + pattern[i - 1][1]] != feld[y + pattern[i][0]][x + pattern[i][1]])
                            okay = false;
                    } catch (IndexOutOfBoundsException e) {
                        okay = false;
                    }
                }
                if (okay && feld[y + pattern[0][0]][x + pattern[0][1]] != '0')
                    return feld[y + pattern[0][0]][x + pattern[0][1]];
            }
        }
        return 'l';
    }
}
