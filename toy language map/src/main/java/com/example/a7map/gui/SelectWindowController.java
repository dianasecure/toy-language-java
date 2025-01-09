package com.example.a7map.gui;

import controller.Controller;
import model.adt.*;
import model.expressions.*;
import model.state.PrgState;
import model.statements.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.MyIRepository;
import repository.Repository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectWindowController implements Initializable {
    @FXML
    private Button selectBttn;
    @FXML
    private ListView<IStatement> selectItemListView;

    private MainWindowController mainWindowController;

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @FXML
    private IStatement selectExample(ActionEvent actionEvent) {
        return selectItemListView.getItems().get(selectItemListView.getSelectionModel().getSelectedIndex());
    }

    private List<IStatement> initExamples(){
        List<IStatement> list = new ArrayList<>();

        IStatement ex1 = new CompStatement(new VarDeclStatement("v",new IntType()),
                new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v")))
        );

        IStatement ex2 = new CompStatement(
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
                                ))));

        IStatement ex3 = new CompStatement(
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

        IStatement ex4 = new CompStatement(
                new VarDeclStatement("varf", new StringType()),new CompStatement(
                new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))), new CompStatement(
                new OpenRFileStatement(new VariableExpression("varf")), new CompStatement(
                new VarDeclStatement("varc", new IntType()), new CompStatement(
                new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompStatement(
                new PrintStatement(new VariableExpression("varc")), new CompStatement(
                new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompStatement(
                new PrintStatement(new VariableExpression("varc")), new CloseRFileStatement(new VariableExpression("varf"))
        ))))))));


        IStatement ex5 = new CompStatement(
                new VarDeclStatement("v", new IntType()), new CompStatement(
                new AssignStatement("v", new ValueExpression(new IntValue(4))), new CompStatement(
                new WhileStatement(
                        new RelationalExpression(new VariableExpression("v"),
                                RelationalOperation.GREATER, new ValueExpression(new IntValue(0))),
                        new AssignStatement("v", new ArithmeticExpression(
                                new VariableExpression("v"), ArithmeticOperation.MINUS, new ValueExpression(new IntValue(1)))
                        )
                ), new PrintStatement(new VariableExpression("v"))
        )));


        IStatement ex6 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())), new CompStatement(
                new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))), new CompStatement(
                new VarDeclStatement("a", new RefType(new RefType(new IntType()))), new CompStatement(
                new HeapAllocationStatement("a", new VariableExpression("v")), new CompStatement(
                new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a"))
        )))));


        IStatement ex7 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())), new CompStatement(
                new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))), new CompStatement(
                new VarDeclStatement("a", new RefType(new RefType(new IntType()))), new CompStatement(
                new HeapAllocationStatement("a", new VariableExpression("v")), new CompStatement(
                new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                new PrintStatement(new ArithmeticExpression(
                        new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))),
                        ArithmeticOperation.PLUS,
                        new ValueExpression(new IntValue(5))))
        )))));

        IStatement ex8 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())), new CompStatement(
                new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))), new CompStatement(
                new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))), new CompStatement(
                new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                new PrintStatement(new ArithmeticExpression(
                        new HeapReadingExpression(new VariableExpression("v")),
                        ArithmeticOperation.PLUS,
                        new ValueExpression(new IntValue(5))
                )
                )
        ))));

        IStatement ex9 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                new HeapAllocationStatement("v", new ValueExpression(new IntValue(40)))
                        )));


        IStatement ex10 = new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(
                new VarDeclStatement("a", new RefType(new IntType())), new CompStatement(
                new AssignStatement("v", new ValueExpression(new IntValue(10))), new CompStatement(
                new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))), new CompStatement(
                new ForkStatement(new CompStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                        new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))), new CompStatement(
                                new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))
                        )))), new CompStatement(
                new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))
        ))))));


        list.add(ex1);
        list.add(ex2);
        list.add(ex3);
        list.add(ex4);
        list.add(ex5);
        list.add(ex6);
        list.add(ex7);
        list.add(ex8);
        list.add(ex9);
        list.add(ex10);
        return list;
    } // add statements to a list

    private void displayExamples(){
        List<IStatement> examples = initExamples();
        selectItemListView.setItems(FXCollections.observableArrayList(examples));
        selectItemListView.getSelectionModel().select(0);
        selectBttn.setOnAction(actionEvent -> {
            int index = selectItemListView.getSelectionModel().getSelectedIndex();
            IStatement selectedProgram = selectItemListView.getItems().get(index);
            index++;
            PrgState programState = createPrgState(selectedProgram); //
            MyIRepository repository = new Repository("log" + index + ".txt");
            Controller controller = new Controller(repository);
            controller.addProgramState(programState);
            try {
                selectedProgram.typecheck(new MyDictionary<String, IType>());
                mainWindowController.setController(controller);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
                alert.show();
            }
            mainWindowController.setController(controller);
        });
    }

    private static PrgState createPrgState(IStatement statement) {
        MyIStack<IStatement> execStack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<String> outputList = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeap heap = new MyHeap();
        return new PrgState(statement, execStack, symTable, outputList, fileTable, heap);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayExamples();
    }
}