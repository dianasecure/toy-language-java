package repository;

import exceptions.RepositoryException;
import model.state.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements MyIRepository{
    private List<PrgState> instances;
    public String logFilePath;

    public Repository(String logFilePath) {
        instances = new ArrayList<PrgState>();
        this.logFilePath = logFilePath;
    }

    public void addState(PrgState state){
        instances.add(state);
    }

    public void logPrgStateExec(PrgState prg) throws RepositoryException{
        try{
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)));
            pw.println(prg.toString());
            pw.close();
        }
        catch(IOException e){
            throw new RepositoryException(e.getMessage());
        }
    }

    public List<PrgState> getPrgList(){
        return instances;
    }

    public void setPrgList(List<PrgState> prgList){
        instances = prgList;
    }

    @Override
    public PrgState getProgramWithId(int id) {
        for(PrgState prg : instances){
            if(prg.getId() == id){
                return prg;
            }
        }
        return null;
    }

}