package controller;

import exceptions.ControllerException;
import exceptions.ExpressionException;
import exceptions.RepositoryException;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.state.PrgState;
import model.statements.IStatement;
import model.value.IValue;
import model.value.RefValue;
import repository.MyIRepository;
import repository.Repository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private MyIRepository repository;
    private ExecutorService executor;

    public Controller(MyIRepository repository) {
        this.repository = repository;
    }

    public MyIRepository getRepository() {
        return repository;
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prg-> repository.logPrgStateExec(prg));

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try { return future.get();}
                    catch(Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(p -> p!=null)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);

        prgList.forEach(prg -> repository.logPrgStateExec(prg));
        repository.setPrgList(prgList);

    }


    public void allStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList=removeCompletedPrg(repository.getPrgList());
        while(!prgList.isEmpty()){
            conservativeGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
            prgList=removeCompletedPrg(repository.getPrgList());
        }

        executor.shutdownNow();
        repository.setPrgList(prgList);
    }

    public void conservativeGarbageCollector(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prg -> {prg.getHeap().setContent(safeGarbageColl(getAllActiveAddr(
                    prg.getSymTable().getValues(), prg.getHeap()), prg.getHeap().getContent()));});

    }


    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }
    

    public Map<Integer, IValue> safeGarbageColl(List<Integer> activeAddresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> activeAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public List<Integer> getAllActiveAddr(Collection<IValue> symTableValues, MyIHeap heap) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> (RefValue) v)
                .flatMap(
                        v -> {
                            List<Integer> addresses = new ArrayList<>();
                            while (true) {
                                if (v.getAddr() == 0) {
                                    break;
                                }
                                addresses.add(v.getAddr());
                                try {
                                    IValue nextVal = heap.get(v.getAddr());
                                    if (nextVal instanceof RefValue) {
                                        v = (RefValue) nextVal;
                                    } else {
                                        break;
                                    }
                                } catch (ExpressionException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            return addresses.stream();
                        }
                )
                .collect(Collectors.toList());
    }



    public void addProgramState(PrgState state) {
        repository.addState(state);
    }
}