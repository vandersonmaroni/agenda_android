package io.maroni.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.maroni.agenda.model.Aluno;

/**
 * Created by vgmaroni on 12/09/2016.
 */
public class AlunoDao extends SQLiteOpenHelper {
    public AlunoDao(Context context) {
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL, caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql);

        }
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoAluno(aluno);

        db.insert("Alunos", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        return dados;
    }

    public List<Aluno> buscaAlunos() {
        List<Aluno> alunos = new ArrayList<Aluno>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Alunos;";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }
        cursor.close();
        return alunos;
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {aluno.getId().toString()};
        db.delete("Alunos", "id = ?", params);
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoAluno(aluno);
        String[] params = {
                aluno.getId().toString()
        };
        db.update("alunos", dados, "id= ?", params);
    }
}
