package plataforma.reporte.model;

import java.util.List;

public class RootList {
   public List<Root> listaRoot;

    public RootList() {
    }

    public List<Root> getListaRoot() {
        return listaRoot;
    }

    public void setListaRoot(List<Root> listaRoot) {
        this.listaRoot = listaRoot;
    }

    @Override
    public String toString() {
        return "RootList{" +
                "listaRoot=" + listaRoot +
                '}';
    }
}
