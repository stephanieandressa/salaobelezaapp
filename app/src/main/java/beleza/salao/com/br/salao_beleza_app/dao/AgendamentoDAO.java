package beleza.salao.com.br.salao_beleza_app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import beleza.salao.com.br.salao_beleza_app.entity.Agendamento;
import beleza.salao.com.br.salao_beleza_app.util.DatabaseHelper;


public class AgendamentoDAO implements DAO<Agendamento>{

    private DatabaseHelper helper;

    public static final String NOME_TABELA = "agendamento";

    public AgendamentoDAO(Context contexto) {
        helper = new DatabaseHelper(contexto);
    }

    @Override
    public Boolean salvar(Agendamento agendamento) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cliente", agendamento.getCliente());
        values.put("email", agendamento.getEmail());
        values.put("servico", agendamento.getServico());
        values.put("data", agendamento.getData());
        values.put("horario", agendamento.getHorario());

        long inseriu = db.insert(NOME_TABELA, null, values);
        return inseriu > 0;
    }

    @Override
    public Boolean remover(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String idAgentamento = String.valueOf(id);
        int removeu = db.delete(NOME_TABELA, "_id = ?", new String[]{idAgentamento});
        return removeu > 0;
    }

    @Override
    public Boolean atualizar(Agendamento agendamento) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", agendamento.getId());
        values.put("cliente", agendamento.getCliente());
        values.put("email", agendamento.getEmail());
        values.put("servico", agendamento.getServico());
        values.put("data", agendamento.getData());
        values.put("horario", agendamento.getHorario());
        String idAgendamento = String.valueOf(agendamento.getId());
        int atualizou = db.update(NOME_TABELA, values, "_id = ?", new String[]{idAgendamento});
        if (atualizou > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<Agendamento> listar() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, cliente, email, servico, data, horario FROM " + NOME_TABELA + " ORDER BY data ASC, horario ASC", null);
        cursor.moveToFirst();
        List<Agendamento> agendamentos = new ArrayList<>();

        for (int i = 0; i < cursor.getCount(); i++){
            Agendamento a = new Agendamento();
            a.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            a.setCliente(cursor.getString(cursor.getColumnIndex("cliente")));
            a.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            a.setServico(cursor.getString(cursor.getColumnIndex("servico")));
            a.setData(cursor.getString(cursor.getColumnIndex("data")));
            a.setHorario(cursor.getString(cursor.getColumnIndex("horario")));

            agendamentos.add(a);
            cursor.moveToNext();
        }
        cursor.close();
        return agendamentos;
    }

    @Override
    public Agendamento obterPorId(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String idAgendamento = String.valueOf(id);
        Cursor cursor = db.rawQuery("SELECT _id, cliente, email, servico, data, horario FROM " + NOME_TABELA + " WHERE _id = ?", new String[]{idAgendamento});

        Agendamento a = null;
        if (cursor.moveToNext()){
            a = new Agendamento();
            a.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            a.setCliente(cursor.getString(cursor.getColumnIndex("cliente")));
            a.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            a.setServico(cursor.getString(cursor.getColumnIndex("servico")));
            a.setData(cursor.getString(cursor.getColumnIndex("data")));
            a.setHorario(cursor.getString(cursor.getColumnIndex("horario")));
        }
        cursor.close();
        return a;
    }
}
