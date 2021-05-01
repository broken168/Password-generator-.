package com.educationalprojects.passwordgenerator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.educationalprojects.passwordgenerator.databinding.ActivityMainBinding
import kotlin.random.Random

@ExperimentalStdlibApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var length: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.seekBarLength.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.textLength.text = "Tamanho: $progress"
                length = progress

                if(boxCheckeds().size > 0)
                    binding.textPassword.text = newPassword(progress)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if(boxCheckeds().size == 0)
                    Toast.makeText(applicationContext, "É preciso selecionar uma opção", Toast.LENGTH_LONG).show()
            }
        })

        binding.buttonGenerate.setOnClickListener {
            if(boxCheckeds().size == 0)
                Toast.makeText(applicationContext, "É preciso selecionar uma opção", Toast.LENGTH_LONG).show()
            else
                binding.textPassword.text = newPassword(length)

        }

    }


    private fun newPassword(length: Int): String {
        val vet = CharArray(length+1)
        for (i: Int in 0..length) {
            vet[i] = randomChar()
        }
        return String(vet)
    }

    private fun randomChar(): Char {

        val list = boxCheckeds()

        val type = list[Random.nextInt(list.size)]
        return when (type) {
            1 -> { // generates a lower letter
                Char(Random.nextInt(26) + 97)
            }
            0 -> { // generates a upper letter
                Char((Random.nextInt(26) + 65))
            }
            2 -> { //generates a number
                Char(Random.nextInt(10) + 48)
            }
            else -> { // generates a special character
                Char((Random.nextInt(15) + 33))
            }
        }
    }

    private fun boxCheckeds(): MutableList<Int> {
        val selecteds = mutableListOf<Int>()
        if(binding.checkBoxUpperLetters.isChecked) selecteds.add(0)
        if(binding.checkBoxLowerLetters.isChecked) selecteds.add(1)
        if(binding.checkBoxNumbers.isChecked) selecteds.add(2)
        if(binding.checkBoxSpecialCharacters.isChecked) selecteds.add(3)
        return selecteds
    }
}