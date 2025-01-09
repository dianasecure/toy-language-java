package repository;

import exceptions.RepositoryException;
import model.state.PrgState;

import java.util.List;

public interface MyIRepository {
    void addState(PrgState state);
    void logPrgStateExec(PrgState prg) throws RepositoryException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> l);

    PrgState getProgramWithId(int id);
}