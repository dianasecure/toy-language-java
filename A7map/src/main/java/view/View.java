package view;

import controller.Controller;
import model.adt.*;
import model.expressions.ArithmeticExpression;
import model.expressions.ArithmeticOperation;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.state.PrgState;
import model.statements.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.MyIRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.util.Scanner;

public class View {
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    private final IStatement ex1= new CompStatement(new VarDeclStatement("v",new IntType()),
            new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))),
                    new PrintStatement(new VariableExpression("v")))
    );

    private final IStatement ex2= new CompStatement(
            new VarDeclStatement("a", new IntType()),
            new CompStatement(
                    new VarDeclStatement("b", new IntType()),
                    new CompStatement(
                            new AssignStatement("a", new ArithmeticExpression(
                                    new ValueExpression(new IntValue(2)),
                                    ArithmeticOperation.PLUS,
                                    new ArithmeticExpression(
                                            new ValueExpression(new IntValue(3)),
                                            ArithmeticOperation.MULTIPLY,
                                            new ValueExpression(new IntValue(5))
                                    )
                            )),
                            new CompStatement(
                                    new AssignStatement("b", new ArithmeticExpression(
                                            new VariableExpression("a"),
                                            ArithmeticOperation.PLUS,
                                            new ValueExpression(new IntValue(1))
                                    )),
                                    new PrintStatement(new VariableExpression("b"))
                            )
                    )
            )
    );

    private final IStatement ex3 = new CompStatement(
            new VarDeclStatement("a", new BoolType()),
            new CompStatement(
                    new VarDeclStatement("v", new IntType()),
                    new CompStatement(
                            new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                            new CompStatement(
                                    new IfStatement(
                                            new VariableExpression("a"),
                                            new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                            new AssignStatement("v", new ValueExpression(new IntValue(3)))
                                    ),
                                    new PrintStatement(new VariableExpression("v"))
                            )
                    )
            )
    );

    /* String varf;
        varf="test.in";
        int varc;
        readFile(varf,varc);print(varc);
        readFile(varf,varc);print(varc)
        closeRFile(varf) */

    private final IStatement test1 = new CompStatement(
            new VarDeclStatement("varf", new StringType()),new CompStatement(
                    new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))), new CompStatement(
                            new OpenRFileStatement(new VariableExpression("varf")), new CompStatement(
                                new VarDeclStatement("varc", new IntType()), new CompStatement(
                                        new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompStatement(
                                                   new PrintStatement(new VariableExpression("varc")), new CompStatement(
                                                           new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompStatement(
                                                                    new PrintStatement(new VariableExpression("varc")), new CloseRFileStatement(new VariableExpression("varf"))
                         )
                      )
                   )
                )
             )
          )
       )
    );

    public void display() throws InterruptedException {
        while (true){
            System.out.println("1.input a program\n2.complete execution of program\n9.exit\n");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                    System.out.println("1.choose prebuilt program\n2.write program\n");
                    int choice2 = sc.nextInt();
                    if (choice2 == 1){
                        System.out.println("""
                                1.Program1: int v; v=2;Print(v)
                                2.Program2: int a;int b; a=2+3*5;b=a+1;Print(b)
                                3.Program3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
                                4.test1 with files
                                """);
                        int choice3 = sc.nextInt();
                        PrgState prgState = null;
                        if (choice3 == 1){
                            prgState = createProgramState(ex1);
                        }
                        else if (choice3 == 2){
                            prgState = createProgramState(ex2);
                        }
                        else if (choice3 == 3){
                            prgState = createProgramState(ex3);
                        }
                        else if (choice3 == 4){
                            prgState = createProgramState(test1);
                        }

                        if (prgState != null) {
                            controller.addProgramState(prgState);
                            System.out.println("Program loaded successfully.\n");
                        } else {
                            System.out.println("Invalid program choice.");
                        }
                    }
                    else{
                        System.out.println("Invalid choice");
                    }
                    break;
                case 2:
                    controller.allStep();
                    break;
                case 9:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
    private PrgState createProgramState(IStatement statement) {
        MyIStack<IStatement> execStack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<String> outputList = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeap heap = new MyHeap();
        return new PrgState(statement, execStack, symTable, outputList, fileTable, heap);
    }
}
