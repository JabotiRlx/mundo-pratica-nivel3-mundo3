/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cadastrobd;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridicaDAO;
import java.util.ArrayList;

/**
 *
 * @author sendi
 */
public class CadastroBD {

    public static void main(String[] args) {
         try {
            Menu menu = new Menu();
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO( );
            PessoaJuridicaDAO pessoaJuridicaDAO= new PessoaJuridicaDAO( );
            
            while (true) {
                menu.telaInicial();
                menu.setTelaInicialResposta();
                
                switch( menu.getTelaInicialResposta() ) {
                    case "1" -> {
                            String tipoPessoa = menu.escolhaTipoPessoa();
                            try{
                                if( "F".equals(tipoPessoa) ) pessoaFisicaDAO.incluir(menu.preenchePessoaFisica() );
                                if( "J".equals(tipoPessoa) ) pessoaJuridicaDAO.incluir(menu.preenchePessoaJuridica() );
                            }catch (Exception e) {
                                System.out.println("O sistema nao pode incluir a pessoa especificada!");
                            }
                        }
                    case "2" -> {
                            int id = menu.inputApenasNumeros( "ID" );
                            String tipoPessoa = menu.escolhaTipoPessoa();
                            try{
                                if( "F".equals(tipoPessoa) ) pessoaFisicaDAO.alterar( id,menu.preenchePessoaFisica() );
                                if( "J".equals(tipoPessoa) ) pessoaJuridicaDAO.alterar( id,menu.preenchePessoaJuridica() );
                            }catch (Exception e) {
                                System.out.println("O sistema nao pode alterar a pessoa especificada!");
                            }
                        }
                    case "3" -> {
                            int id = menu.inputApenasNumeros( "ID" );
                            String tipoPessoa = menu.escolhaTipoPessoa();
                            
                            try{
                                if( "F".equals(tipoPessoa) ) pessoaFisicaDAO.excluir( id );
                                if( "J".equals(tipoPessoa) ) pessoaJuridicaDAO.excluir( id );
                            } catch (Exception e) {
                                System.out.println("O sistema nao pode excluir a pessoa especificada!");
                            }
                        }
                    case "4" -> {
                            int id = menu.inputApenasNumeros( "ID" );
                            String tipoPessoa = menu.escolhaTipoPessoa();
                            
                            try{
                                if( "F".equals(tipoPessoa) ){
                                    PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id );
                                    pessoaFisica.exibir();
                                }

                                if( "J".equals(tipoPessoa) ){
                                    PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id );
                                    pessoaJuridica.exibir();
                                }
                             } catch (Exception e) {
                                System.out.println("O sistema nao pode encontrar a pessoa especificada!");
                            }
                        }
                    case "5" -> {
                            String tipoPessoa = menu.escolhaTipoPessoa();
                            try{
                                if( "F".equals(tipoPessoa) ){
                                    ArrayList<PessoaFisica> pessoasFisicas =  pessoaFisicaDAO.getPessoas();
                                    pessoasFisicas.stream().forEach( pessoa -> pessoa.exibir() );
                                }

                                if( "J".equals(tipoPessoa) ) {
                                    ArrayList<PessoaJuridica> pessoasJuridicas =  pessoaJuridicaDAO.getPessoas();
                                    pessoasJuridicas.stream().forEach( pessoa -> pessoa.exibir() );
                                }
                            } catch (Exception e) {
                                System.out.println("O sistema nao pode encontrar todas as pessoa!");
                            }
                        }
         
                    case "0" -> System.exit(0);
                    default -> System.out.println("Opcao invalida!");
                }
                
            }
        } catch(IllegalStateException  e) {
            // System.in has been closed
            System.out.println("System.in was closed; exiting");
        }
        
    }
    
}
