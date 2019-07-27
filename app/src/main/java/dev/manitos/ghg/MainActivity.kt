package dev.manitos.ghg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.manitos.ghg.mvvm.OteloFragment

class MainActivity: AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportFragmentManager
      .beginTransaction()
      .add(R.id.myrcontainer, OteloFragment.newInstance())
      .commitAllowingStateLoss()
  }
}
