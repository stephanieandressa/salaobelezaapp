package beleza.salao.com.br.salao_beleza_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beleza.salao.com.br.salao_beleza_app.dao.AgendamentoDAO;
import beleza.salao.com.br.salao_beleza_app.entity.Agendamento;

public class MainActivity extends AppCompatActivity {

    private AlertDialog alertDialog, dialogConfirmacao;
    private AgendamentoDAO dao;
    List<Map<String, Object>> agendamentos;
    private int agendamentoSelecionado;
    private ListView listView;

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(
                AdapterView<?> adapterView,
                View view, int position, long id) {
            agendamentoSelecionado = position;
            alertDialog.show();
        }
    };

    private DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int item) {
            Integer id = (Integer) agendamentos.get(agendamentoSelecionado).get("id");
            switch (item){
                case 0:
                    Intent intent = new Intent(getApplicationContext(),
                            NovoAgendamentoActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    break;
                case 1:
                    dialogConfirmacao.show();
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    boolean sucesso = dao.remover(id);
                    if (sucesso){
                        agendamentos.remove(agendamentoSelecionado);
                        Toast.makeText(getApplicationContext(), R.string.remover_sucesso,
                                Toast.LENGTH_LONG).show();
                        dialogConfirmacao.dismiss();
                    }
                    listView.invalidateViews();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    dialogConfirmacao.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NovoAgendamentoActivity.class);
                startActivity(intent);
            }
        });

        //____________________________________Busca os agendamentos no banco, joga dentro de um SimpleAdapter
        //Seta o adapter na lista e seta o listener ao clicar em um item da lista

        dao = new AgendamentoDAO(getApplicationContext());
        listView = (ListView) findViewById(R.id.calendar_list);
        String[] de = {"data", "horario", "cliente", "servico"};
        int[] para = {R.id.data_agendamento, R.id.horario_agendamento, R.id.cliente_agendamento, R.id.servico_agendamento};
        this.agendamentos = this.listarAgendamento();
        SimpleAdapter adapter = new SimpleAdapter(this, this.agendamentos, R.layout.layout_agendamento, de, para);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);
        //__________________________________Até aqui

        this.alertDialog = criaAlertDialog();
        this.dialogConfirmacao = criaDialogConfirmacao();
    }

    private List<Map<String, Object>> listarAgendamento(){
        List<Map<String, Object>> agendamentos = new ArrayList<Map<String, Object>>();
        List<Agendamento> listaAgendamentos = dao.listar();
        if (listaAgendamentos == null || listaAgendamentos.isEmpty()){
            return agendamentos;
        }
        for (Agendamento a : listaAgendamentos){
            Map<String, Object> item = new HashMap<>();
            item.put("id", a.getId());
            item.put("cliente", a.getCliente());
            item.put("email", a.getEmail());
            item.put("servico", a.getServico());
            item.put("data", a.getData());
            item.put("horario", a.getHorario());
            agendamentos.add(item);
        }
        return agendamentos;
    }

    private AlertDialog criaAlertDialog(){
        final CharSequence[] items = {
                getString(R.string.editar),
                getString(R.string.remover)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.opcoes);
        builder.setItems(items, listener2);

        return builder.create();
    }

    private AlertDialog criaDialogConfirmacao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmacao_exclusao_agendamento);
        builder.setPositiveButton(getString(R.string.sim), listener2);
        builder.setNegativeButton(getString(R.string.nao), listener2);

        return builder.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
