package beleza.salao.com.br.salao_beleza_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import beleza.salao.com.br.salao_beleza_app.dao.AgendamentoDAO;
import beleza.salao.com.br.salao_beleza_app.entity.Agendamento;

public class NovoAgendamentoActivity extends AppCompatActivity {

    private Integer idAgendamento;
    private TextView cliente;
    private EditText email;
    private TextView servico;
    private EditText data;
    private EditText horario;
    private Button salvar;
    private Button cancelar;
    private AgendamentoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_agendamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cliente = findViewById(R.id.cliente_nome);
        email = findViewById(R.id.cliente_email);
        servico = findViewById(R.id.servico);
        data = findViewById(R.id.data);
        horario = findViewById(R.id.horario);
        salvar = findViewById(R.id.salvar);
        cancelar = findViewById(R.id.cancelar);

        dao = new AgendamentoDAO(getApplicationContext());
        Intent intent = getIntent();
        idAgendamento = intent.getIntExtra("id", 0);
        if (idAgendamento > 0) {
            Agendamento agendamento = dao.obterPorId(idAgendamento);
            cliente.setText(agendamento.getCliente());
            email.setText(agendamento.getEmail());
            servico.setText(agendamento.getServico());
            data.setText(agendamento.getData());
            horario.setText(agendamento.getHorario());
        } else {

            cliente.setText("");
            email.setText("");
            servico.setText("");
            data.setText("");
            horario.setText("");
        }


        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Agendamento agendamento = new Agendamento();
                agendamento.setCliente(cliente.getText().toString());
                agendamento.setEmail(email.getText().toString());
                agendamento.setServico(servico.getText().toString());
                agendamento.setData(data.getText().toString());
                agendamento.setHorario(horario.getText().toString());
                AgendamentoDAO dao = new AgendamentoDAO(getApplicationContext());
                boolean sucesso = dao.salvar(agendamento);
                if (sucesso){
                    Toast.makeText(getApplicationContext(), "Agendamento cadastrado com sucesso", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao cadastrar agendamento", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
