import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AoC2024Day6 {
    public static int arrowArray;
    public static int arrowIndex;
    public static int totalPositions;
    public static int runOnce = 1;
    public static int ran = 0;
    public static void main(String[] args){
        try{
            File file = new File("Aoc2024D6Input.txt");
            Scanner arrays = new Scanner(file);

            //This scanner will be used to get the amt of arrays
            Scanner scanLength = new Scanner(file);
            int numArrays = getNumArrays(scanLength);
            String[][] mat = new String[numArrays][];

            for(int i = 0; i < numArrays; i++){
                String arrayStr = arrays.nextLine();
                mat[i] = arrayStr.split("");
            }

            getAPosition(mat);

            String direction = mat[arrowArray][arrowIndex];

            int positions = numPositions(mat, numArrays, direction);
            System.out.println("The guard will visit " + positions + " distinct positions.");
            arrays.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found.");
        }
    }
    
    public static int getNumArrays(Scanner scan){
        int total = 0;
        while(scan.hasNextLine()){
            total++;
            scan.nextLine();
        }
        return total;
    }




    //Loops through each array in the matrix to find which array the starting arrow is in and what index it is at.
    public static void getAPosition(String[][] mat){
        //r = row  c = column
        for(int r = 0; r < mat.length; r++){
            for(int c = 0; c < mat[r].length; c++){

                if(mat[r][c].equals("^")){
                    //Sets the value of the array and index locations of the arrow.
                    arrowArray = r;
                    arrowIndex = c;
                }
            }
           
        }   
    }

    public static int numPositions(String[][] mat, int numArrays, String direction){
        

        if(arrowIndex <=0 && arrowIndex < mat[1].length && arrowArray <= numArrays && arrowArray > 0){
            //Print out the map
            System.out.println();
            for(int r = 0; r < mat.length; r++){
                for(int c = 0; c < mat[r].length; c++){
                    System.out.print(mat[r][c]);
                }
                System.out.println();
            }
            System.out.println();
            //adds one more to account for the space that the guard ended on.
            totalPositions++;
            return totalPositions;
        }else{
            int arrowArrayOG = arrowArray;
            int arrowIndexOG = arrowIndex;
            boolean moved = false;
            String check = "";
            //up
            if(direction.equals("^") && !moved){
                check = mat[arrowArray-1][arrowIndex];
                if(check.equals(".")){
                    mat[arrowArray-1][arrowIndex] = direction;
                    arrowArray--;
                    moved = true;
                }else if(check.equals("#")){
                    //Turn 90 degrees
                    mat[arrowArray][arrowIndex] = ">";
                }else{
                    mat[arrowArray-1][arrowIndex] = direction;
                    arrowArray--;
                    mat[arrowArrayOG][arrowIndexOG] = "x";
                }
            }
            //down
            if(direction.equals("v") && !moved){
                check = mat[arrowArray+1][arrowIndex];
                if(check.equals(".")){
                    mat[arrowArray+1][arrowIndex] = direction;
                    arrowArray++;
                    moved = true;
                }else if(check.equals("#")){
                    //Turn 90 degrees
                    mat[arrowArray][arrowIndex] = "<";
                }else{
                    mat[arrowArray+1][arrowIndex] = direction;
                    arrowArray++;
                    mat[arrowArrayOG][arrowIndexOG] = "x";
                }
            }
            //left
            if(direction.equals("<") && !moved){
                check = mat[arrowArray][arrowIndex-1];
                if(check.equals(".")){
                    mat[arrowArray][arrowIndex-1] = direction;
                    arrowIndex--;
                    moved = true;
                }else if(check.equals("#")){
                    //Turn 90 degrees
                    mat[arrowArray][arrowIndex] = "^";
                }else{
                    mat[arrowArray][arrowIndex-1] = direction;
                    arrowIndex--;
                    mat[arrowArrayOG][arrowIndexOG] = "x";
                }
            }
            //right
            if(direction.equals(">") && !moved){
                check = mat[arrowArray][arrowIndex+1];
                if(check.equals(".")){
                    mat[arrowArray][arrowIndex+1] = direction;
                    arrowIndex++;
                    moved = true;
                }else if(check.equals("#")){
                    //Turn 90 degrees
                    mat[arrowArray][arrowIndex] = "v";
                }else{
                    mat[arrowArray][arrowIndex+1] = direction;
                    arrowIndex++;
                    mat[arrowArrayOG][arrowIndexOG] = "x";
                }
            }
            //If the guard moved, mark where he originally was and add to the total positions.
            if(moved){
                mat[arrowArrayOG][arrowIndexOG] = "x";
                totalPositions++;
            }

            return numPositions(mat, numArrays, mat[arrowArray][arrowIndex]);
        }
    }
}