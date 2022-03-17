package io.github.axis567_lang.rssfeed

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
//      CLASE PARA INGRESAR LOS DATOS
class FeedEntry
{
    // VARIABLES
    var name: String = ""
    var artist: String = ""
    var releaseDate: String = ""
    var summary: String = ""
    var imageURL: String = ""

    override fun toString(): String {
        return """
            name = $name
        """.trimIndent()
    }
}

class MainActivity : AppCompatActivity()
{
    private val TAG = "TAG"
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object
    {
        private class DownloadData : AsyncTask<String, Void, String>()
        {
            private val TAG = "DownloadData"

            override fun doInBackground(vararg url: String?): String
            {
                Log.d(TAG, "doInBackground")

                val rssFeed = downloadXML(url[0])
                if(rssFeed.isEmpty())
                {
                    Log.e(TAG, "doInBackground: failed")
                }
                return rssFeed
            }

            override fun onPostExecute(result: String?)
            {
                super.onPostExecute(result)
                Log.d(TAG, "onPostExecute")
            }

            private fun downloadXML(urlPath: String?): String
            {
                // VERSIÓN 1
                /*
                val xmlResult = StringBuilder()
                try
                {
                    val url = URL(urlPath)

                    // OPENING
                    var connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                    val response = connection.responseCode
                    Log.d(TAG, "downloadXML response code was $response")

                    //  BUFFER OF READ INPUT: This saves the input in order to lighten the weight of
                    //                        resources use. Temporarily saves data so processes
                    //                        don't access the same data in the same place.
                    //  A buffer is a data area shared by hardware devices or program processes
                    //  that operate at different speeds or with different sets of priorities.
                    //  The buffer allows each device or process to operate without being held up
                    //  by the other.
                    /*
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))

                    val inputBuffer = CharArray(500)
                    var charsRead = 0

                    // In case there's no data; we want to keep going
                    while(charsRead <= 0)
                    {
                        charsRead = reader.read(inputBuffer)

                        // Append the read input
                        if (charsRead > 0)
                        {
                            xmlResult.append(String(inputBuffer, 0, charsRead))
                        }
                    }
                    reader.close()
                    */
                    //
                    // inputStream buffers the connection result and creates a
                    // variable wiht type StringBuffer
                    connection.inputStream.buffered().reader().use { reader ->
                        xmlResult.append(reader.readText())
                    }

                    Log.d(TAG, "Received ${xmlResult.length} bytes")
                    return xmlResult.toString()
                }
                /*
                catch (e: MalformedURLException)
                {
                    Log.e(TAG, "downloadXML: Invalid url: ${e.message}")
                }
                catch (e: IOException)
                {
                    Log.e(TAG, "downloadXML: Error reading data: ${e.message}")
                }
                catch (e: Exception)
                {
                    Log.e(TAG, "downloadXML: Unknown Error: ${e.message}")
                }*/
                // UN TIPO DE SWITCH PARA LOS ERRORES: evitar múltiples "catch"
                catch(e: Exception)
                {
                    val errorMessage: String = when (e)
                    {
                        is MalformedURLException -> "downloadXML: Invalid URL: ${e.message}"
                        is IOException -> "downloadXML: Error reading data: ${e.message}"
                        else -> "downloadXML: Unknown Error: ${e.message}"
                    }
                    Log.e(TAG, errorMessage)
                }
                 */

                // VERSIÓN 2: RECEPCIÓN DE DATOS
                try
                {
                    // readText() hace su propio buffer y variable para guardar los datos
                    return URL(urlPath).readText()
                }
                catch (e: Exception)
                {
                    val errorMessage: String = when (e)
                    {
                        is MalformedURLException -> "downloadXML: Invalid URL: ${e.message}"
                        is IOException -> "downloadXML: Error reading data: ${e.message}"
                        else -> "downloadXML: Unknown Error: ${e.message}"
                    }
                    Log.e(TAG, errorMessage)
                }
                return ""
            }// downloadXML


        }// class DownloadData
    }// Companion Obj
}