package br.ufms.gitpay.data.firebase;

import br.ufms.gitpay.data.firebase.config.FirebaseConfig;
import br.ufms.gitpay.domain.model.banco.Banco;
import br.ufms.gitpay.domain.model.conta.DadosConta;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

public class FiresforeRefs {

    private static final Firestore db = FirebaseConfig.getFirestore();

    public static CollectionReference getBancoCollection() {
        return db.collection("bancos");
    }

    public static CollectionReference getChavePixCollection() {
        return db.collection("chavesPix");
    }

    public static CollectionReference getUsuarioCollection() {
        return getBancoGitPayDocument().collection("usuarios");
    }

    public static CollectionReference getContaCollection(String codigoBanco) {
        return getBancoCollection().document(codigoBanco).collection("contas");
    }

    public static CollectionReference getContaGitPayCollection() {
        return getBancoGitPayDocument().collection("contas");
    }

    public static CollectionReference getTransacaoCollection() {
        return getBancoGitPayDocument().collection("transacoes");
    }

    public static DocumentReference getBancoDocument(String codigoBanco) {
        return getBancoCollection().document(codigoBanco);
    }

    public static DocumentReference getChavePixDocument(String chavePix) {
        return getTransacaoCollection().document(chavePix);
    }

    public static DocumentReference getBancoGitPayDocument() {
        return getBancoCollection().document(Banco.GitPay.getCodigo());
    }

    public static DocumentReference getUsuarioDocument(String cpfCnpj) {
        return getUsuarioCollection().document(cpfCnpj);
    }

    public static DocumentReference getContaDocument(DadosConta dados) {
        return getContaCollection(dados.banco()).document(dados.numero() + dados.tipo().getAbreviacao());
    }

    public static DocumentReference getContaGitPayDocument(int numeroConta) {
        return getBancoCollection().document(Banco.GitPay.getCodigo());
    }

    public static DocumentReference getTransacaoDocument(String id) {
        return getTransacaoCollection().document(id);
    }
}
