import java.util.Arrays;
import java.util.Scanner;
import java.lang.Math;


public class Array2D {
    int[][] board;
    int[][] chain_status;
    int h;
    int w;

    public Array2D(int m, int n) {
        this.board = new int[m][n];
        this.chain_status = new int[m][n];
        this.h = m;
        this.w = n;
        System.out.printf("\nboard created %d * %d\n",board.length,board[0].length);
    }

    public void setNum(int val, int m, int n) {
        this.board[m][n] = val;
    }

    public void setNum(int val, String s) {
        for (int i = 0; i < this.h; i++) {
            for (int j = 0; j < this.w; j++) {
                if (this.board[i][j] == val) {
                    if(s.equals("2*1")) {this.board[i][j] *= -1; this.board[i+1][j] *= -1;
                    this.chain_status[i][j] = 21;}
                    if(s.equals("1*2")) {this.board[i][j] *= -1; this.board[i][j+1] *= -1;
                    this.chain_status[i][j] = 12;}
                    if(s.equals("2*2")) {
                        this.board[i][j] *= -1; this.board[i][j+1] *= -1;
                        this.board[i+1][j] *= -1; this.board[i+1][j+1] *= -1;
                        this.chain_status[i][j] = 22;
                    }
                 }
            }
        }
    }

    public void print() {
        System.out.println(Arrays.deepToString(this.board));
    }

    public boolean moveCheck(int row, int col, String direction) {
        if (this.board[row][col] > 0) {
            return check(row,col,direction);
        }
        //如果是大块，这个办法的row col参数只能是左上角的
        if (this.board[row][col] < 0) {
            if (direction.equals("U")) {
                if (this.chain_status[row][col] == 12 || this.chain_status[row][col] == 22) {
                    return check(row,col,"U") && check(row,col+1,"U");
                }
            }
            if (direction.equals("D")) {
                if (this.chain_status[row][col] == 21) {
                    return check(row-1,col,"D");
                }
                if(this.chain_status[row][col] == 22) {
                    return check(row-1,col,"D") && check(row-1,col+1,"D");
                }
                if(this.chain_status[row][col] == 12) {
                    return check(row,col,"D") && check(row,col+1,"D");
                }
            }
            if (direction.equals("L")) {
                if (this.chain_status[row][col] == 21 || this.chain_status[row][col] == 22) {
                    return check(row,col,"L") && check(row+1,col,"L");
                }
            }
            if (direction.equals("R")) {
                if (this.chain_status[row][col] == 21) {
                    return check(row,col,"R") && check(row,col+1,"R");
                }
                if(this.chain_status[row][col] == 22) {
                    return check(row,col+1,"R") && check(row+1,col+1,"R");
                }
                if(this.chain_status[row][col] == 12) {
                    return check(row,col+1,"R");
                }
            }
        }
        return false;
    }

    public boolean check(int row, int col, String d) {
        if (d.equals("U")) {return this.board[Math.max(0,row-1)][col] == 0;}
        if (d.equals("D")) {return this.board[Math.min(h-1,row+1)][col] == 0;}
        if (d.equals("L")) {return this.board[row][Math.max(0,col-1)] == 0;}
        if (d.equals("R")) {return this.board[row][Math.min(w-1,col+1)] == 0;} //防止越界
        return false;
    }

    public void move(int row, int col, String d, int[][] arr) {
        if(d.equals("U")) {swap(row,col,Math.max(0,row-1),col,arr);}
        if(d.equals("D")) {swap(row,col,Math.min(h-1,row+1),col,arr);}
        if(d.equals("L")) {swap(row,col,row,Math.min(0,col-1),arr);}
        if(d.equals("R")) {swap(row,col,row,Math.max(w-1,col+1),arr);}
    }

    public void swap(int row, int col, int row2, int col2, int[][] arr) {
        int temp = arr[row][col];
        arr[row][col] = arr[row2][col2];
        arr[row2][col2] = temp;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        Array2D a = new Array2D(m,n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                a.setNum(sc.nextInt(),i,j);
            }
        }
        int linked = sc.nextInt();
        for (int i = 0; i < linked; i++) {
            a.setNum(sc.nextInt(), sc.next());
        }
        a.print();
        System.out.println(a.moveCheck(2,2,"U"));
        System.out.println(a.moveCheck(2,3,"U"));
        System.out.println(a.moveCheck(0,1,"R"));
    }
}
