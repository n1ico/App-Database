package com.example.appdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.appdatabase.ui.theme.AppDataBaseTheme
import com.example.appdatabase.viewModel.PessoaViewModel
import com.example.appdatabase.viewModel.Repository
import androidx.lifecycle.ViewModelProvider
import roomDB.Pessoa
import roomDB.PessoaDataBase
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.lifecycle.ViewModel



class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            PessoaDataBase::class.java,
            "pessoa.db"
        ).build()
    }

    private val viewModel by viewModels<PessoaViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PessoaViewModel(Repository(db)) as T
                }
            }
        }
    )

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
        AppDataBaseTheme {
            Surface( modifier = Modifier.fillMaxSize(),color = colorScheme.background ){
                screen(viewModel, this)
            }
        }
    }
}
}
@Composable
fun screen(viewModel: PessoaViewModel, mainActivity: MainActivity) {
     var nome = ""
     var telefone = ""
     var pessoa = Pessoa(nome, telefone)

   Column(
       Modifier
           .background(Color.White)
   ) {
       Row(
           Modifier
               .padding(20.dp)
       ) {}
           Row(
               Modifier
                   .fillMaxWidth(),
                   Arrangement.Center
           ){
               Text(
                   text = "App DataBase",
                   fontWeight = FontWeight.Bold,
                   fontSize = 30.sp
               )
           }
           Row(
               Modifier
                   .padding(20.dp)
           ){}
               Row(
                   Modifier
                       .fillMaxWidth(),
                     Arrangement.Center
               ){
                   TextField(
                       value = nome,
                       onValueChange = { nome = it },
                       label = { Text("Nome:") }
                   )
               }
               Row(
                   Modifier
                       .fillMaxWidth(),
                     Arrangement.Center
               ){
                   TextField(
                       value = telefone,
                       onValueChange = { telefone = it },
                       label = { Text("Telefone:") }
                   )
               }
               Row(
                   Modifier
                       .padding(20.dp)
                       .fillMaxWidth(),
                   Arrangement.Center
               ){
                   Button(onClick = {
                       viewModel.upsertPessoa(pessoa)
                       nome = ""
                       telefone = ""
                   }){
                       Text(text = "Cadastrar")
                   }
               }
   }
}