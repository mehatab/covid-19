package info.covid.uicomponents

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentBindingDelegate<T : ViewDataBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T>, LifecycleObserver {
    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        fragment.viewLifecycleOwnerLiveData.observe(fragment, Observer {
            it.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    binding = null
                }
            })
        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        fragment.lifecycle.removeObserver(this)
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val binding = binding
        if (binding != null) {
            return binding
        }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
        }

        return viewBindingFactory(thisRef.requireView()).also {
            this@FragmentBindingDelegate.binding = it
        }
    }
}

fun <T : ViewDataBinding> Fragment.bind(viewBindingFactory: (View) -> T) =
    FragmentBindingDelegate(this, viewBindingFactory)

/**
 *  How to use?
 *  private val binding by viewBinding(MyFragmentBinding::bind)
 * */

