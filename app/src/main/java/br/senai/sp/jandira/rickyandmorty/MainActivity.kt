package br.senai.sp.jandira.rickyandmorty

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.rickyandmorty.service.RetrofitFactory
import br.senai.sp.jandira.rickyandmorty.ui.theme.RickyAndMortyTheme
import retrofit2.Callback

import br.senai.sp.jandira.rickyandmorty.model.CharacterList
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import retrofit2.Call
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickyAndMortyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(227, 178, 236, 255)
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    var resultsState = remember {
        mutableStateOf(listOf<br.senai.sp.jandira.rickyandmorty.model.Character>())
    }

    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Button(
            onClick = {

                val call = RetrofitFactory().getCharactersService().getCharacters()

                call.enqueue(object: Callback<CharacterList>{
                    override fun onResponse(
                        call: Call<CharacterList>,
                        response: Response<CharacterList>
                    ) {
                        resultsState.value = response.body()!!.results
                        Log.i("xpto", resultsState.value[0].toString())
                    }

                    override fun onFailure(call: Call<CharacterList>, t: Throwable) {
                        Log.i("xpto", t.message.toString())
                    }


                })

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Carregar lista")
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()){
            items(resultsState.value){
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(size = 16.dp)
                ) {
                    Row {
                        Card(
                            modifier = Modifier.padding(8.dp),
                            shape = CircleShape,
                            border = BorderStroke(width = 2.dp, color = Color.Magenta)
                        ) {
                            AsyncImage(model = it.image, contentDescription = "")
                        }
                        Column(modifier = Modifier.padding(start = 8.dp, top = 4.dp, bottom = 4.dp)) {
                            Row(
                                modifier = Modifier.padding(bottom = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                //Image(painter = painterResource(id = R.drawable.person_24), contentDescription = "")
                                Text(
                                    text = it.name,
                                    color = Color.Magenta,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                //Image(painter = painterResource(id = R.drawable.group_work_24), contentDescription = "")
                                Text(
                                    text = it.species,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                            Text(
                                text = it.location.name,
                                color = Color.Blue,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RickyAndMortyTheme {
        Greeting("Android")
    }
}