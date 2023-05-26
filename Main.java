public class Main {
    public static void main(String[] args) {

        String method = "bc";
        String filename = "Try2_1.txt";

        switch (method) {
            case "tt":
                HornKB kb = new HornKB(filename);
                TT tt = new TT(kb);

                boolean entailTrue = tt.TT_ENTAILS();
                if (entailTrue) {
                    int num_true = tt.getCounter();
                    System.out.println("YES: " + num_true);
                } else {
                    System.out.println("NO");
                }
                break;

            case "fc":
                HornKB kb1 = new HornKB(filename);
                FC fc = new FC(kb1);
                fc.FC_Check();
                break;

            case "bc":
                HornKB kb2 = new HornKB(filename);
                BC bc = new BC(kb2);
                bc.BC_Check();
                break;

            case "gkb":
                GeneralKB gkb = new GeneralKB(filename);
                Boolean result = gkb.evaluatePostfix();

                if (result) {
                    System.out.println("YES: " + gkb.getCounter());
                } else {
                    System.out.println("NO");
                }
                break;

            default:
                System.out.println("Invalid method entered.");
                break;
        }
    }
}
