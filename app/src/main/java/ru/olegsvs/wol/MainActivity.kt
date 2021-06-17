package ru.olegsvs.wol

import android.app.Activity
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Toast
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        wakeUp()
        finish()
    }

    private fun wakeUp(
        ip: String = BuildConfig.LAN_BROADCAST_MASK,
        mac: String = BuildConfig.LAN_DST_MAC_ADDRESS
    ) {
        DatagramSocket().use { socket ->
            try {
                val macBytes = getMacBytes(mac)
                val bytes = ByteArray(6 + 16 * macBytes.size)
                for (i in 0..5) {
                    bytes[i] = 0xff.toByte()
                }
                var i = 6
                while (i < bytes.size) {
                    System.arraycopy(macBytes, 0, bytes, i, macBytes.size)
                    i += macBytes.size
                }
                val address: InetAddress = InetAddress.getByName(ip)
                val packet = DatagramPacket(bytes, bytes.size, address, 9)
                socket.send(packet)
                Toast.makeText(this, "Wake-on-LAN packet sent.", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Failed to send Wake-on-LAN packet: $e", Toast.LENGTH_LONG).show()
            }
        }
    }

    @Throws(IllegalArgumentException::class)
    private fun getMacBytes(macStr: String): ByteArray {
        val bytes = ByteArray(6)
        val hex = macStr.split(":").toTypedArray()
        require(hex.size == 6) { "Invalid MAC address." }
        try {
            for (i in 0..5) {
                bytes[i] = hex[i].toInt(16).toByte()
            }
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Invalid hex digit in MAC address. ${e.toString()}")
        }
        return bytes
    }
}