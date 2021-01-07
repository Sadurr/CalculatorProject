package sample;

import com.sun.media.sound.SF2InstrumentRegion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigInteger;

public class Controller {

    @FXML
    private TextArea tareaMain;

    @FXML
    private Button BtnEq;

    @FXML
    private RadioButton RBtn10;

    @FXML
    private RadioButton RBtn16;

    @FXML
    private RadioButton RBtn2;

    @FXML
    private TextField tareaLast;

    @FXML
    private TextField tareaOperation;

    final ToggleGroup group = new ToggleGroup();

    private final DataModel _model = new DataModel();

    private int systemBase = 10;
    private int prevSystemBase = 10;

    @FXML
    void AddDigitToInput(ActionEvent event) {
        BigInteger inputText = new BigInteger(tareaMain.getText());
        String btnTxt = ((Button)event.getSource()).getText();
        if(inputText.compareTo(BigInteger.valueOf(Integer.valueOf(0)))==0) {
            tareaMain.setText(btnTxt);
        }
        else {
            tareaMain.setText(inputText + btnTxt);
        }
    }

    @FXML
    void ChangeNumberBase(ActionEvent event) {
        initialise();
        String sys = ((RadioButton)group.getSelectedToggle()).getText();
        prevSystemBase = systemBase;
        if (sys.equals("binary")) systemBase = 2;
        else if (sys.equals("decimal")) systemBase = 10;
        else if (sys.equals("hexal")) systemBase = 16;

        tareaMain.setText(convertFromBaseToBase(tareaMain.getText(),prevSystemBase,systemBase));
        String tareaLastText = tareaLast.getText();
        if(!tareaLastText.equals("")){
            tareaLast.setText(convertFromBaseToBase(tareaLast.getText(), prevSystemBase, systemBase));
        }
    }

    private void initialise(){
        RBtn2.setToggleGroup(group);
        RBtn10.setToggleGroup(group);
        RBtn16.setToggleGroup(group);
    }

    @FXML
    void RemoveLastInputDigit(ActionEvent event) {
        String inputText = tareaMain.getText();
        if (inputText.equals("0")){

        } else if(inputText.length()==1) {
            tareaMain.setText("0");
        } else {
            tareaMain.setText(inputText.substring(0,inputText.length()-1));
        }
    }

    @FXML
    void changeCurrentBinaryOperation(ActionEvent event) {
        String lastComputed = tareaMain.getText();
        tareaLast.setText(lastComputed);
        tareaMain.setText("0");
        tareaOperation.setText(((Button)event.getSource()).getText());
    }

    @FXML
    void clearInput(ActionEvent event) {
        tareaMain.setText("0");
        tareaOperation.setText("");
        tareaLast.setText("");
    }

    @FXML
    void executeBinaryOperation(ActionEvent event) {
        String input1 = tareaLast.getText();
        String input2 = tareaMain.getText();

        if (input1.equals("") || input2.equals("")) return;

        if (systemBase != 10) {
            input1 = convertFromBaseToBase(input1, systemBase, 10);
            input2 = convertFromBaseToBase(input2, systemBase, 10);
        }

        BigInteger secondOperand = new BigInteger(input2);
        BigInteger firstOperand = new BigInteger(input1);

        BigInteger result;
        try {

            switch (tareaOperation.getText()) {
                case "+":
                    result = _model.add(firstOperand, secondOperand);
                    break;
                case "-":
                    result = _model.subtract(firstOperand, secondOperand);
                    break;
                case "/":
                    result = _model.divide(firstOperand, secondOperand);
                    break;
                case "*":
                    result = _model.multiply(firstOperand, secondOperand);
                    break;
                case "mod":
                    result = _model.moduloDivide(firstOperand, secondOperand);
                    break;
                case "a^b":
                    result = _model.power(firstOperand, secondOperand);
                    break;
                case "(n,k)":
                    result = _model.newton(firstOperand, secondOperand);
                    break;
                default:
                    result = new BigInteger("0");
            }

            String resultString = result.toString();
            if(systemBase !=10) {
                resultString = convertFromBaseToBase(result.toString(),10,systemBase);
            }

            tareaMain.setText(resultString);
            tareaLast.setText(input2);
            tareaOperation.setText("");

        } catch (Exception e) {
            tareaOperation.setText("Too big to compute...");
        }
    }


    private String convertFromBaseToBase(String str,int fromBase, int toBase) {
        return Integer.toString(Integer.parseInt(str, fromBase),toBase);
    }


    @FXML
    void executeUnaryOperation(ActionEvent event) {
        String firstOperandString = tareaMain.getText();

        if(systemBase!=10) {
            firstOperandString = convertFromBaseToBase(firstOperandString, systemBase, 10);
        }

        BigInteger firstOperand = new BigInteger(firstOperandString);
        String operator = ((Button)event.getSource()).getText();

        long unit = -1;
        long zero = 0;

        BigInteger result;

        try {
            switch(operator) {
                case "+/-":
                    result = firstOperand.multiply(BigInteger.valueOf(unit));
                case "n!":
                    result = _model.factorial(firstOperand);
                    break;
                default:
                    result = BigInteger.valueOf(zero);
            }
            String resultString = "";
            if(systemBase != 10) {
                resultString = convertFromBaseToBase(result.toString(),10,systemBase);
            }
            else {
                resultString = result.toString();
            }

            tareaMain.setText(resultString);
        }
        catch (Exception e) {
            tareaOperation.setText("Too big to compute...");
        }

    }

}

