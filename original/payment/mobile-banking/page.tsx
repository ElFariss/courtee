"use client"

import type React from "react"

import { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import { ArrowLeft, ChevronUp, ChevronDown, Copy } from "lucide-react"
import { Button } from "@/components/ui/button"
import { SuccessToast } from "@/components/success-toast"
import { LeaveConfirmationModal } from "@/components/leave-confirmation-modal"

export default function MobileBankingPaymentPage() {
  const router = useRouter()
  const [timeLeft, setTimeLeft] = useState(3600)
  const [guideOpen, setGuideOpen] = useState(true)
  const virtualAccount = "1204-34248-3235"
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

  const copyToClipboard = () => {
    navigator.clipboard.writeText(virtualAccount.replace(/-/g, ""))
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
    "Buka aplikasi mobile banking anda",
    "Pilih menu transaksi",
    "Pilih virtual account",
    "Pilih SeaBank sebagai bank tujuan",
    "Masukkan nomor virtual account",
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
          Silahkan gunakan aplikas mobile banking pilihan anda untuk menyelesaikan pembayaran.
        </p>

        {/* Payment Info */}
        <div className="mb-8">
          <p className="text-[#9957B3]">
            Nominal yang harus dibayarkan <span className="font-bold">Rp 55.100,00</span>
          </p>
          <p className="text-[#9957B3]">
            Selesaikan pembayaran dalam <span className="font-bold">{formatTime(timeLeft)}</span>
          </p>
        </div>

        {/* Virtual Account Card */}
        <div className="bg-[#f9fafb] rounded-lg p-6 mb-8 border border-[#EAECF0] shadow-sm">
          <div className="flex items-center gap-3 mb-4">
            <div className="w-10 h-10 bg-gradient-to-r from-orange-400 to-pink-500 rounded-full flex items-center justify-center">
              <span className="text-white font-bold text-xs">S</span>
            </div>
            <span className="font-semibold text-[#9957B3]">SeaBank</span>
            <span className="text-muted-foreground">|</span>
            <span className="text-muted-foreground text-sm">Virtual Account</span>
          </div>
          <div className="flex items-center justify-between">
            <span className="text-2xl font-bold text-foreground tracking-wider">{virtualAccount}</span>
            <button onClick={copyToClipboard} className="p-2 hover:bg-muted rounded-lg transition-colors">
              <Copy className="h-5 w-5 text-muted-foreground" />
            </button>
          </div>
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
