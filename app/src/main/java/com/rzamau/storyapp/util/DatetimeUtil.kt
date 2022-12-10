import android.content.Context
import com.rzamau.storyapp.R
import kotlinx.datetime.*

class DatetimeUtil {
    private fun now(): LocalDateTime {
        val currentMoment: Instant = Clock.System.now()
        return currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun localDateTimetoString(date: LocalDateTime?): String {
        return date.toString()
        //yyyy-MM-dd HH:mm:ss.SSS
    }

    fun humanizeDatetime(context: Context, date: LocalDateTime?): String {
        val sb = StringBuilder()
        date?.run {
            val hour = if (this.hour > 12) {
                (this.hour - 12).toString() + ":" + this.minute + " pm"
            } else {
                this.hour.toString() + ":" + this.minute + " am"
            }
            val today = now()
            val tomorrow =
                Clock.System.now().plus(1, DateTimeUnit.DAY, TimeZone.currentSystemDefault())
                    .toLocalDateTime(TimeZone.currentSystemDefault())
            if (this.date == today.date) {
                sb.append("${context.getText(R.string.today_at)} $hour")
            } else if (this.date == tomorrow.date) {
                sb.append("${context.getText(R.string.tomorrow_at)} $hour")
            } else {
                sb.append(this.date.month.name.lowercase() + " ${this.date.dayOfMonth}")
            }
        } ?: sb.append(context.getString(R.string.unknown))
        return sb.toString()
    }

}