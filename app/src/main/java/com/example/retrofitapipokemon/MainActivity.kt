package com.example.retrofitapipokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.retrofitapipokemon.databinding.ActivityMainBinding
import com.example.retrofitapipokemon.retrofit.Pokemon
import com.example.retrofitapipokemon.retrofit.RestEngine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuscar.setOnClickListener {
            if(binding.txtPokemon.text.toString() != ""){
                binding.progressBar.visibility = View.VISIBLE
                invocarAPI(binding.txtPokemon.text.toString())
            }
            else{
                Toast.makeText(applicationContext, "Ingrese un Pokemon", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun invocarAPI(pokemon: String) {
            CoroutineScope(Dispatchers.IO).launch{
                val llamada: PokemonAPIService = RestEngine.getRestEngine().create(PokemonAPIService::class.java)
                val resultado: Call<Pokemon> = llamada.obtenerPokemon(pokemon)
                val p: Pokemon? = resultado.execute().body()

                if(p != null){
                    runOnUiThread {
                        binding.txtBaseEsperience.text = p.base_experience.toString()
                        binding.txtid.text = p.id.toString()
                        binding.txtName.text = p.name
                        binding.txtSprite.text = "SPRITES: \n"
                        binding.txtSprite.append(p.sprites.back_default + "\n")
                        binding.txtSprite.append(p.sprites.back_female + "\n")
                        binding.txtSprite.append(p.sprites.back_shiny + "\n")
                        binding.txtSprite.append(p.sprites.back_shiny_female + "\n")
                        binding.txtSprite.append(p.sprites.front_default + "\n")
                        binding.txtSprite.append(p.sprites.front_female + "\n")
                        binding.txtSprite.append(p.sprites.front_shiny + "\n")
                        binding.txtSprite.append(p.sprites.front_shiny_female + "\n")


                    }
            }
    }
}
}