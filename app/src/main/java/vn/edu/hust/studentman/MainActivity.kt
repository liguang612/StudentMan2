package vn.edu.hust.studentman

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import vn.edu.hust.studentman.Constant.RequestCode

class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentAdapter = StudentAdapter(this, students)
        val listView = findViewById<ListView>(R.id.list_view_students)
        listView.adapter = studentAdapter
        registerForContextMenu(listView)

        registerForContextMenu(listView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                val it = Intent(this, AddUpdateActivity::class.java)
                it.putExtra("request_code", RequestCode.CREATE)

                startActivityForResult(it, RequestCode.CREATE.code)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val student = students[info.position]

        return when (item.itemId) {
            R.id.menu_edit -> {
                val it = Intent(this, AddUpdateActivity::class.java)
                it.putExtra("request_code", RequestCode.UPDATE)
                it.putExtra("name", student.name)
                it.putExtra("id", student.id)
                it.putExtra("pos", info.position)

                startActivityForResult(it, RequestCode.UPDATE.code)

                true
            }
            R.id.menu_delete -> {
                students.removeAt(info.position)
                studentAdapter.notifyDataSetChanged()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    @Deprecated("")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_CANCELED) return;

        val name = data?.getStringExtra("name").toString()
        val id = data?.getStringExtra("id").toString()
        if (requestCode == RequestCode.CREATE.code) {
            students.add(StudentModel(name, id))
            studentAdapter.notifyDataSetChanged()
        } else if(requestCode == RequestCode.UPDATE.code) {
            val pos = data?.getIntExtra("pos", -1);

            if (pos == null || pos == -1) return;
            else {
                students[pos].name = name
                students[pos].id = id

                studentAdapter.notifyDataSetChanged()
            }
        }
    }
}