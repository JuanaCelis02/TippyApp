package com.example.tippyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import com.example.tippyapp.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.seekBarTip.progress = INITIAL_TIP_PERCENT
        binding.tvTipPercentLabel.text = "$INITIAL_TIP_PERCENT%"
        binding.seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //Imprime en LogCat el valor de la barra de progreso
                Log.i(TAG, "onProgressChanged $progress")
                binding.tvTipPercentLabel.text = "$progress%"
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        binding.etBaseAmount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                //Obtengo lo que el usuario esta escribiendo
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
            }

        })
    }

    private fun computeTipAndTotal() {
        if(binding.etBaseAmount.text.isEmpty()){
            binding.tvTipAmount.text = ""
            binding.tvTotalAmount.text = ""
            return
        }
        //1. Obtener el valor de la factura y el porcentaje de propina
        val baseAmount = binding.etBaseAmount.text.toString().toDouble()
        val tipPercent = binding.seekBarTip.progress
        //2. Calcular la propina y total
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount
        //3. Update the UI
        binding.tvTipAmount.text = "%.3f".format(tipAmount)
        binding.tvTotalAmount.text = "%.3f".format(totalAmount)
    }

}