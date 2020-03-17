import app.util.BaseSchedulers
import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider : BaseSchedulers() {
    override fun computation() = Schedulers.trampoline()
    override fun ui() = Schedulers.trampoline()
    override fun io() = Schedulers.trampoline()
    override fun newThread() = Schedulers.trampoline()
}
