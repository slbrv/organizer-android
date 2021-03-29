package com.slbrv.organizer.ui.task

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.icu.text.DateTimePatternGenerator.PatternInfo.OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.slbrv.organizer.Config
import com.slbrv.organizer.R
import java.util.*


class TaskEditDialogFragment(val data: MutableLiveData<TaskListFragment.TaskEditData>) :
    BottomSheetDialogFragment() {

    companion object {
        fun getInstance(data: MutableLiveData<TaskListFragment.TaskEditData>)
                : TaskEditDialogFragment {
            val fragment = TaskEditDialogFragment(data)
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var task = ""
    private var projectName = ""
    private var targetDate = Date()

    private lateinit var taskEditText: EditText
    private lateinit var taskTargetDateButton: Button
    private lateinit var taskProjectButton: Button
    private lateinit var taskAddButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Organizer_BottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_edit_dialog, container, false)

        taskEditText = view.findViewById(R.id.task_dialog_edit_text)
        taskTargetDateButton = view.findViewById(R.id.task_dialog_target_date_button)
        taskProjectButton = view.findViewById(R.id.task_dialog_project_button)
        taskAddButton = view.findViewById(R.id.task_dialog_add_button)

        taskTargetDateButton.setOnClickListener {
            onTargetDateButtonClick()
        }

        taskProjectButton.setOnClickListener {
            onProjectButtonClick()
        }

        taskAddButton.setOnClickListener {
            onTaskAddButtonClick()
        }

        return view
    }

    private fun onTargetDateButtonClick() {
        val listener = { picker: DatePicker, y: Int, m: Int, d: Int ->
            val c = Calendar.getInstance()
            c.set(y, m, d)
            targetDate = c.time
            taskTargetDateButton.text = "$y-$m-$d"
        }
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(requireContext(), listener, year, month, day)
        dialog.show()
    }

    private fun onProjectButtonClick() {
        val projectEditText = EditText(requireContext())
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.project_name)
            .setPositiveButton(R.string.ok) { d, id ->
                projectName = projectEditText.text.toString()
                if(projectName.length > Config.UI.MAX_PROJECT_NAME_LENGTH)
                    projectName = projectName.substring(0, Config.UI.MAX_PROJECT_NAME_LENGTH)
                taskProjectButton.text = projectName
            }
            .setNegativeButton(R.string.cancel) { d, id ->
                dialog?.cancel()
            }
        dialog.setView(projectEditText).show()
    }

    private fun onTaskAddButtonClick() {
        task = taskEditText.text.toString()
        if(task.isNotEmpty())
            data.value = TaskListFragment.TaskEditData(task, projectName, targetDate)
        dismiss()
    }
}