package scr;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 4, 2008
 * Time: 4:59:21 PM

 */
public class DeadSimpleSoloController extends Controller {

    final double targetSpeed = 35;
    final ArrayList<Pair> llistaDades= new ArrayList<>();
    final ArrayList<Pair> mitjanaDades= new ArrayList<>();

    public Action control(SensorModel sensorModel) {
        Action action = new Action ();
        if (sensorModel.getSpeed () < targetSpeed) {
            action.accelerate = 1;
        }
        if (sensorModel.getTrackPosition() == 0) {
            action.steering = 0.0; 
        }
        else if (sensorModel.getTrackPosition() > 0){
            action.steering = - 0.3;
        }
        else action.steering = 0.3;
        action.gear = 1;
        recollirDades(action.steering,sensorModel.getDistanceFromStartLine());
        return action;
    }

    public void reset() {
        System.out.println("Restarting the race!");	
    }

    public void shutdown(){
        try {
            System.out.println("Bye bye!");
            ferMitjana();
            escriureDades();
        } catch (IOException ex) {
            Logger.getLogger(DeadSimpleSoloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void escriureDades()
        throws IOException
    {
            FileWriter fileWriter = new FileWriter("dades.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for(int i=0; i<mitjanaDades.size();i++){
                Pair x = mitjanaDades.get(i);
                printWriter.printf("%f %f \n",x.getKey(),x.getValue());
            }
            printWriter.close();
    }
    public void ferMitjana(){
        int mida = llistaDades.size();
        double acumulat=0;
        
        for(int j=0;j<9;j++){
            llistaDades.add(llistaDades.get(j));
        }
        for(int i=0; i<mida;i++){
            acumulat=0;
            for (int t=0;t<10;t++){
                acumulat+=(Double)llistaDades.get(i+t).getKey();
            }
            Pair aux=new Pair(acumulat/10,llistaDades.get(i).getValue());
            mitjanaDades.add(aux);
        }
    }
    public void recollirDades(double gir, double pos){
       Pair p= new Pair(gir, pos);
       llistaDades.add(p);
    }
    
}
