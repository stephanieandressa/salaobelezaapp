package beleza.salao.com.br.salao_beleza_app.dao;

import java.util.List;


public interface DAO<T> {

    Boolean salvar(T objeto);

    Boolean remover (int id);

    Boolean atualizar (T objeto);

    List<T> listar();

    T obterPorId(int id);

}
