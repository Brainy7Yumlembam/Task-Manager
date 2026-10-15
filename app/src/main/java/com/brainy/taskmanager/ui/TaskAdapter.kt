package com.brainy.taskmanager.ui

import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brainy.taskmanager.R
import com.brainy.taskmanager.data.Priority
import com.brainy.taskmanager.data.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Listener interface for handling user interactions with a task item.
 */
interface TaskClickListener {
    fun onTaskCheckedChanged(task: Task, isChecked: Boolean)
    fun onEditClick(task: Task)
    fun onDeleteClick(task: Task)
}

/**
 * RecyclerView adapter for displaying tasks with modern card styling,
 * priority badges, overdue indicators, strike-through animations, and action handling.
 */
class TaskAdapter(
    private val listener: TaskClickListener
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    private val dateFormat = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cbCompleted: CheckBox = itemView.findViewById(R.id.cbCompleted)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvDueDateTime: TextView = itemView.findViewById(R.id.tvDueDateTime)
        private val tvOverdueBadge: TextView = itemView.findViewById(R.id.tvOverdueBadge)
        private val tvPriority: TextView = itemView.findViewById(R.id.tvPriority)
        private val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEditClick(getItem(position))
                }
            }

            btnEdit.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEditClick(getItem(position))
                }
            }

            btnDelete.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(getItem(position))
                }
            }
        }

        fun bind(task: Task) {
            val context = itemView.context

            // Title
            tvTitle.text = task.title

            // Description: hide if empty
            if (task.description.isNotBlank()) {
                tvDescription.text = task.description
                tvDescription.visibility = View.VISIBLE
            } else {
                tvDescription.visibility = View.GONE
            }

            // Due Date & Time: format with dd-MMM-yyyy or hide if null
            val formattedDate = com.brainy.taskmanager.util.DateUtils.formatTaskDueDisplay(task.dueDateTime, task.dueDate)
            if (formattedDate != null) {
                tvDueDateTime.text = formattedDate
                tvDueDateTime.visibility = View.VISIBLE
            } else {
                tvDueDateTime.visibility = View.GONE
            }

            // Overdue badge detection
            val isOverdue = !task.isCompleted && task.dueDateTime != null && task.dueDateTime < System.currentTimeMillis()
            if (isOverdue) {
                tvOverdueBadge.visibility = View.VISIBLE
                tvDueDateTime.setTextColor(ContextCompat.getColor(context, R.color.priority_high))
            } else {
                tvOverdueBadge.visibility = View.GONE
                tvDueDateTime.setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
            }

            // Priority badge styling with modern rounded capsule
            tvPriority.text = task.priority.name
            val (textColorRes, bgColorRes, borderColorRes) = when (task.priority) {
                Priority.HIGH -> Triple(R.color.priority_high, R.color.priority_high_bg, R.color.priority_high_border)
                Priority.MEDIUM -> Triple(R.color.priority_medium, R.color.priority_medium_bg, R.color.priority_medium_border)
                Priority.LOW -> Triple(R.color.priority_low, R.color.priority_low_bg, R.color.priority_low_border)
            }
            val textColor = ContextCompat.getColor(context, textColorRes)
            val bgColor = ContextCompat.getColor(context, bgColorRes)
            val borderColor = ContextCompat.getColor(context, borderColorRes)

            tvPriority.setTextColor(textColor)

            val badgeDrawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 8f * context.resources.displayMetrics.density
                setColor(bgColor)
                setStroke((1f * context.resources.displayMetrics.density).toInt(), borderColor)
            }
            tvPriority.background = badgeDrawable

            val paddingH = context.resources.getDimensionPixelSize(R.dimen.priority_badge_padding_h)
            val paddingV = context.resources.getDimensionPixelSize(R.dimen.priority_badge_padding_v)
            tvPriority.setPadding(paddingH, paddingV, paddingH, paddingV)

            // Visual completed state
            if (task.isCompleted) {
                tvTitle.paintFlags = tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                tvTitle.setTextColor(ContextCompat.getColor(context, R.color.text_muted))
                itemView.alpha = 0.55f
            } else {
                tvTitle.paintFlags = tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                tvTitle.setTextColor(ContextCompat.getColor(context, R.color.text_primary))
                itemView.alpha = 1.0f
            }

            // Checkbox: clear listener first to prevent spurious triggers during view recycling
            cbCompleted.setOnCheckedChangeListener(null)
            cbCompleted.isChecked = task.isCompleted
            cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onTaskCheckedChanged(getItem(position), isChecked)
                }
            }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}
