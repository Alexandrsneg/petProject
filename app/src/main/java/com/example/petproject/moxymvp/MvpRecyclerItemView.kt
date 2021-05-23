package info.esoft.ko.presentation.common.moxymvp

import android.content.Context
import android.util.AttributeSet
import com.arellomobile.mvp.MvpDelegate
import com.arellomobile.mvp.MvpView
import com.example.petproject.moxymvp.ABaseView

abstract class MvpRecyclerItemView @JvmOverloads constructor(private val parentDelegate: MvpDelegate<*>, context: Context,
                                                             attrs: AttributeSet? = null,
                                                             defStyleAttr: Int = 0) :
        ABaseView(context, attrs, defStyleAttr), MvpView {

    open var mvpChildId: String? = null
        @Synchronized set

    init {
        inject()
    }

    abstract fun inject()

    private var mvpDeledate: MvpDelegate<*>? = null
        get() {
            if (mvpChildId == null)
                return null
            if (field == null) {
                val mvpD = MvpDelegate(this)
                mvpD.setParentDelegate(parentDelegate, mvpChildId)
                field = mvpD

            }
            return field
        }


    fun destroyMvpDelegate() {
        mvpDeledate?.let {
            it.onSaveInstanceState()
            it.onDetach()
            mvpDeledate = null
        }
    }


    fun createMvpDelegate() {
        mvpDeledate?.let {
            it.onCreate()
            it.onAttach()
        }
    }
}