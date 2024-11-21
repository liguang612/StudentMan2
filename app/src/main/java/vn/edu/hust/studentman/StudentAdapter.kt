package vn.edu.hust.studentman

import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
  private val students: MutableList<StudentModel>,
  val menuInflater: MenuInflater,
  val onEditItemClicked: (Int, StudentModel) -> Unit
): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
  inner class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
    val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)

    var pos : Int = -1

    init {
      itemView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
      menuInflater.inflate(R.menu.context_menus, menu)

      if (menu != null) {
        val optEdit = menu.findItem(R.id.menu_edit)
        optEdit.setOnMenuItemClickListener {
          onEditItemClicked(position, students[position])

          true
        }

        val optDelete = menu.findItem(R.id.menu_delete)
        optDelete.setOnMenuItemClickListener {
          students.removeAt(position)
          notifyItemRemoved(position)

          true
        }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
       parent, false)
    return StudentViewHolder(itemView)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.textStudentName.text = student.studentName
    holder.textStudentId.text = student.studentId

    holder.pos = position
  }
}