package beleza.salao.com.br.salao_beleza_app.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import beleza.salao.com.br.salao_beleza_app.dao.AgendamentoDAO;

/**
 * Created by tiozao on 10/12/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "BancoKibeleza";
    private static int VERSAO = 1;

    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + AgendamentoDAO.NOME_TABELA + " (" +
                "_id INTEGER PRIMARY KEY," +
                "cliente TEXT," +
                "email TEXT," +
                "servico TEXT," +
                "data TEXT," +
                "horario TEXT" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
