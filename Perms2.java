import java.util.ArrayList;

public class Perms2 {

    public static void main(String[] args) {
        int n = 6;
        ArrayList<Integer> list = new ArrayList<Integer>();
        perms( n, list );  // start tree with empty list
    }

    // given target number of items to permute (n),
    // and the list of items we've chosen so far,
    // if appropriate display the list and quit (reached a good list)
    // or generate all children
    private static void perms( int n, ArrayList<Integer> list ) {
        if ( n == list.size() ) {// done!
            for (int k=0; k<list.size(); k++) {
                System.out.print( list.get(k) + "," );
            }
            System.out.println();
        }
        else {// need more items---generate child node (calls)
            for (int k=1; k<=n; k++) {

                if ( ! list.contains( k ) ) {
                    // newList is a copy of list
                    ArrayList<Integer> newList = new ArrayList<Integer>();
                    for (int j=0; j<list.size(); j++) {
                        newList.add( list.get(j) );
                    }
                    newList.add( k );
                    perms( n, newList );
                }
                else {
                }
            }
        }
    }
}
