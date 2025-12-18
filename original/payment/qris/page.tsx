"use client"

import type React from "react"

import { useState, useEffect } from "react"
import Image from "next/image"
import { useRouter } from "next/navigation"
import { ArrowLeft, Download, ChevronUp, ChevronDown } from "lucide-react"
import { Button } from "@/components/ui/button"
import { SuccessToast } from "@/components/success-toast"
import { LeaveConfirmationModal } from "@/components/leave-confirmation-modal"

export default function QRISPaymentPage() {
  const router = useRouter()
  const [timeLeft, setTimeLeft] = useState(3600)
  const [guideOpen, setGuideOpen] = useState(true)
  const [showSuccessToast, setShowSuccessToast] = useState(false)
  const [showLeaveModal, setShowLeaveModal] = useState(false)
  const [isPaymentConfirmed, setIsPaymentConfirmed] = useState(false)

  useEffect(() => {
    const timer = setInterval(() => {
      setTimeLeft((prev) => (prev > 0 ? prev - 1 : 0))
    }, 1000)
    return () => clearInterval(timer)
  }, [])

  const formatTime = (seconds: number) => {
    const mins = Math.floor(seconds / 60)
    const secs = seconds % 60
    return `${mins.toString().padStart(2, "0")}:${secs.toString().padStart(2, "0")}`
  }

  const handleConfirm = () => {
    setIsPaymentConfirmed(true)
    setShowSuccessToast(true)
    setTimeout(() => {
      router.push("/")
    }, 3000)
  }

  const handleBackClick = (e: React.MouseEvent) => {
    if (!isPaymentConfirmed) {
      e.preventDefault()
      setShowLeaveModal(true)
    }
  }

  const handleLeaveConfirm = () => {
    setShowLeaveModal(false)
    router.push("/checkout")
  }

  const paymentGuide = [
    "Buka aplikasi e-wallet anda",
    "Pilih menu bayar melalui QR",
    "Scan QR di atas",
    "Masukan nominal sesuai tagihan anda",
    "Selesaikan transfer",
    "Tekan tombol konfirmasi di bawah",
  ]

  return (
    <div className="min-h-screen bg-background">
      <SuccessToast
        isOpen={showSuccessToast}
        onClose={() => setShowSuccessToast(false)}
        title="Order anda berhasil diproses!"
        description="Anda akan diarahkan ke home. Anda dapat mengakses dashboard untuk mengecek pesanan."
      />

      <LeaveConfirmationModal
        isOpen={showLeaveModal}
        onClose={() => setShowLeaveModal(false)}
        onConfirm={handleLeaveConfirm}
      />

      <div className="container mx-auto px-4 py-8 max-w-2xl">
        {/* Header */}
        <div className="flex items-center gap-4 mb-8">
          <button
            onClick={handleBackClick}
            className="w-10 h-10 rounded-lg bg-[#9957B3] flex items-center justify-center text-white hover:bg-[#874da0] transition-colors"
          >
            <ArrowLeft className="h-5 w-5" />
          </button>
          <h1 className="text-2xl font-bold text-[#9957B3]">Pembayaran</h1>
        </div>

        {/* Instructions */}
        <p className="text-foreground mb-6 leading-relaxed">
          Silahkan scan dan lakukan pembayaran melalui QR Code di bawah menggunakan aplikasi e-wallet pilihan anda.
        </p>

        {/* Payment Info */}
        <div className="mb-6">
          <p className="text-[#9957B3]">
            Nominal yang harus dibayarkan <span className="font-bold">Rp 55.100,00</span>
          </p>
          <p className="text-[#9957B3]">
            Selesaikan pembayaran dalam <span className="font-bold">{formatTime(timeLeft)}</span>
          </p>
        </div>

        {/* QR Code */}
        <div className="flex flex-col items-center mb-6">
          <div className="bg-background p-4 rounded-lg">
            <Image src="/qr-code-payment.jpg" alt="QR Code Payment" width={300} height={300} className="rounded-lg" />
          </div>
          <Button className="mt-4 gap-2 bg-[#9957B3] text-white hover:bg-[#874da0]">
            <Download className="h-4 w-4" />
            Download QR
          </Button>
        </div>

        {/* Payment Guide */}
        <div className="mb-8">
          <button
            onClick={() => setGuideOpen(!guideOpen)}
            className="flex items-center justify-between w-full py-3 text-left"
          >
            <h2 className="text-lg font-semibold text-foreground">Panduan Pembayaran</h2>
            {guideOpen ? (
              <ChevronUp className="h-5 w-5 text-muted-foreground" />
            ) : (
              <ChevronDown className="h-5 w-5 text-muted-foreground" />
            )}
          </button>
          {guideOpen && (
            <ol className="list-decimal list-inside space-y-2 text-muted-foreground mt-2">
              {paymentGuide.map((step, index) => (
                <li key={index}>{step}</li>
              ))}
            </ol>
          )}
        </div>

        <Button
          onClick={handleConfirm}
          disabled={isPaymentConfirmed}
          className="w-full py-6 text-base font-medium bg-[#9957B3] hover:bg-[#874da0] text-white rounded-lg disabled:opacity-50"
        >
          Konfirmasi
        </Button>
      </div>
    </div>
  )
}
