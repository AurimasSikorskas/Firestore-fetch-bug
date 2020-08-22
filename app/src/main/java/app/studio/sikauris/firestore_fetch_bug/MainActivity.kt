package app.studio.sikauris.firestore_fetch_bug

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import app.studio.sikauris.firestore_fetch_bug.databinding.ActivityMainBinding
import com.google.firebase.firestore.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val result = MutableLiveData<LoadResult<String>>()
    private val bookReference: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        result.observe(this, Observer {resultData ->
            binding.swipeRefreshLayout.isRefreshing = false
            (resultData as? LoadResult.Loading)?.let {
                binding.swipeRefreshLayout.isRefreshing = true
            }
            (resultData as? LoadResult.Success<String>)?.let {
                binding.textView.text = it.data
            }
        })

        binding.refresh.setOnClickListener {
            loadData()
        }

        binding.update1.setOnClickListener {
            addVolume()
        }

        binding.update2.setOnClickListener {
            updateBook()
        }
    }

    private fun addVolume() {
        result.postValue(LoadResult.Loading)
        val data = BookModel.getVolume(System.currentTimeMillis())
        bookReference.update("volumes", FieldValue.arrayUnion(data)).addOnCompleteListener {
            result.postValue(LoadResult.Success("ready"))
        }
    }

    private fun updateBook() {
        result.postValue(LoadResult.Loading)
        val data = BookModel.getVolume(System.currentTimeMillis())
        bookReference.update("startedAt", System.currentTimeMillis()).addOnCompleteListener {
            result.postValue(LoadResult.Success("ready"))
        }
    }

    private fun setInitialData() {
        result.postValue(LoadResult.Loading)
        bookReference.set(BookModel.getInitialData(), SetOptions.merge()).addOnCompleteListener {
            result.postValue(LoadResult.Success("ready"))
        }
    }

    private fun loadData() {
        result.postValue(LoadResult.Loading)
        Log.e("MainActivity","fetch started")
        val startTime = System.currentTimeMillis()
        bookReference.get().addOnCompleteListener {
            Log.e("MainActivity","fetch finished")
            val stopTime = System.currentTimeMillis()
            if(it.isSuccessful) {
                result.postValue(LoadResult.Success(
                    "completed in " + TimeUnit.MILLISECONDS.toSeconds(stopTime - startTime))
                )
            } else {
                Log.e("MainActivity","loadData", it.exception)
            }
        }
    }

    init {
        val firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()

        bookReference = firestore.document("user/user1/books/goodBook")
        setInitialData()
    }
}