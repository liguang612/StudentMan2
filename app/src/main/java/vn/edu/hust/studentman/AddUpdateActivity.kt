package vn.edu.hust.studentman

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import vn.edu.hust.studentman.Constant.RequestCode

class AddUpdateActivity : AppCompatActivity() {
    private lateinit var btnCancel: Button
    private lateinit var btnOK: Button

    private lateinit var etName: EditText
    private lateinit var etId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update)

        initUI()
    }

    private fun initUI() {
        btnCancel = findViewById(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            finish()
        }
        btnOK = findViewById(R.id.btn_OK)
        btnOK.setOnClickListener {
            onOK()
        }

        etName = findViewById(R.id.et_name)
        etId = findViewById(R.id.et_id)
        if (intent.getSerializableExtra("request_code") == RequestCode.UPDATE) {
            etName.setText(intent.getStringExtra("name"))
            etId.setText(intent.getStringExtra("id"))
        }
    }

    private fun onOK() {
        val it = Intent()
        it.putExtra("name", etName.text.toString())
        it.putExtra("id", etId.text.toString())
        it.putExtra("pos", intent.getIntExtra("pos", -1))

        setResult(Activity.RESULT_OK, it)
        finish()
    }
}