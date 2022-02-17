package custom

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.core.view.WindowCompat
import com.example.mmcash.databinding.DialogChooseAccountBinding

class ChooseAccountDialog(activity: Activity) {


    private var activity: Context = activity
    private lateinit var dialog: Dialog
    private lateinit var binding: DialogChooseAccountBinding


    private var result: Boolean? = null


    private fun setupDialog() {
        val inflater: LayoutInflater = LayoutInflater.from(activity)
        binding = DialogChooseAccountBinding.inflate(inflater)
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        WindowCompat.setDecorFitsSystemWindows(dialog.window!!, true)
        dialog.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )
        dialog.setContentView(binding.root)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.account1.setOnClickListener {
            result = false
            dialog.dismiss()
        }

        binding.account2.setOnClickListener {
            result = true
            dialog.dismiss()
        }

        dialog.setOnDismissListener {
            onResult?.let { it1 -> it1(result ?: false) }
        }
    }

    private var onResult: ((Boolean) -> (Unit))? = null


    fun setOnResultListener(listener: (Boolean) -> (Unit)) {
        onResult = listener
    }


    fun show() {
        dialog.show()
    }

    init {
        setupDialog()
    }


}