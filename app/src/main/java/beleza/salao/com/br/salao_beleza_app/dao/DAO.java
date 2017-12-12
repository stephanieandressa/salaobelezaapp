package beleza.salao.com.br.salao_beleza_app.dao;

import java.util.List;

/**
 * Created by tiozao on 10/12/17.
 */

public interface DAO<T> {

    Boolean salvar(T objeto);

    Boolean remover (int id);

    Boolean atualizar (T objeto);

    List<T> listar();

    T obterPorId(int id);

}
