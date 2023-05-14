package kg.surfit.carcollection.utils

import android.content.Context
import android.content.SharedPreferences

class SubCounter(val context: Context) : SharedPreferences.OnSharedPreferenceChangeListener {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("sub_counter", Context.MODE_PRIVATE)

    private val KEY_SHOW_CAR_COUNT = "show_count"
    private val KEY_ADD_CAR_COUNT = "add_count"

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == KEY_SHOW_CAR_COUNT || key == KEY_ADD_CAR_COUNT) {
            // Обработка изменений счетчиков
        }
    }

    fun incrementShowCount() {
        val count = getShowCount()
        val newCount = count + 1
        saveShowCount(newCount)
    }

    fun incrementAddCount() {
        val count = getAddCount()
        val newCount = count + 1
        saveAddCount(newCount)
    }

    fun getShowCount(): Int {
        return sharedPreferences.getInt(KEY_SHOW_CAR_COUNT, 0)
    }

    fun getAddCount(): Int {
        return sharedPreferences.getInt(KEY_ADD_CAR_COUNT, 0)
    }

    private fun saveShowCount(count: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_SHOW_CAR_COUNT, count)
        editor.apply()
    }

    private fun saveAddCount(count: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_ADD_CAR_COUNT, count)
        editor.apply()
    }

    fun unregisterListener() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}