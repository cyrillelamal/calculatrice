import com.lebonbus.calculatrice.Controllers.CalculatorController;
import com.lebonbus.calculatrice.Controllers.ControllerInterface;
import com.lebonbus.calculatrice.Models.Calculator;
import com.lebonbus.calculatrice.Models.CalculatorModelInterface;

public class Main {
    public static void main(String[] args) {
        final CalculatorModelInterface model = new Calculator();
        final ControllerInterface controller = new CalculatorController(model);

        controller.run();
    }
}
