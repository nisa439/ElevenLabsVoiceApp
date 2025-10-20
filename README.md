# ElevenLabs TTS Mobil Uygulama

<div align="center">


ElevenLabs'ın gelişmiş metin-konuşma API'sini kullanarak mobil platformlarda stüdyo kalitesinde ses sentezi sunan profesyonel bir uygulama.

[Dokümantasyon](#dokümantasyon) • [Kurulum](#kurulum) • [Özellikler](#özellikler) • [Katkıda Bulunma](#katkıda-bulunma)

</div>

---

## Genel Bakış

Bu Flutter tabanlı mobil uygulama, ElevenLabs'ın son teknoloji metin-konuşma sistemine kusursuz bir arayüz sağlar. Kurumsal düzeyde mimari desenler ve kullanıcı deneyimi en iyi uygulamalarıyla geliştirilmiş olup, modern ve sezgisel bir arayüzle profesyonel ses sentezi yetenekleri sunar.

### Temel Yetenekler

- **Yüksek Kaliteli Ses Sentezi**: İnsan benzeri tonlama ve duygu içeren nöral TTS
- **Çoklu Dil Desteği**: 29 dilde 100+ ses seçeneği
- **Düşük Gecikme**: Flash v2.5 model ile 2 saniyenin altında ses üretimi
- **Gelişmiş Ses Kontrolleri**: Stabilite, benzerlik ve stil parametrelerini ayarlama
- **Üretime Hazır**: Kapsamlı hata yönetimi ile ticari dağıtım için optimize edilmiş

---

## Özellikler

### Ana İşlevsellik

| Özellik | Açıklama |
|---------|----------|
| **Metin Girişi** | Karakter sayımı ve gerçek zamanlı doğrulama ile çok satırlı editör |
| **Ses Kütüphanesi** | Önizleme özelliği ile aranabilir 100+ profesyonel ses kataloğu |
| **Ses Üretimi** | Yapılandırılabilir parametrelerle talep üzerine TTS sentezi |
| **Dosya İçe Aktarma** | TXT, DOC ve DOCX dosya formatları desteği |
| **Ses Yönetimi** | Üretilen ses dosyalarını oynatma, kaydetme ve paylaşma (MP3) |

### Teknik Özellikler

- **Karanlık Mod Arayüzü**: Modern, erişilebilirlik uyumlu arayüz tasarımı
- **Duyarlı Yerleşim**: Çeşitli ekran boyutları ve yönleri için optimize edilmiş
- **Verimli Önbellekleme**: Geliştirilmiş performans için akıllı kaynak yönetimi
- **Hata Kurtarma**: Kullanıcı dostu mesajlarla kapsamlı hata yönetimi
- **Durum Yönetimi**: Uygun ayrım ile temiz mimari

---

## Kurulum

### Gereksinimler

```bash
Flutter SDK: >=3.0.0
Dart SDK: >=3.0.0
ElevenLabs API Anahtarı (https://elevenlabs.io adresinden edinin)
```

### Kurulum Adımları

1. **Depoyu klonlayın**
```bash
git clone https://github.com/kullaniciadi/elevenlabs_tts_app.git
cd elevenlabs_tts_app
```

2. **Bağımlılıkları yükleyin**
```bash
flutter pub get
```

3. **API kimlik bilgilerini yapılandırın**

`assets/.env` dosyası oluşturun:
```env
ELEVENLABS_API_KEY=api_anahtariniz_buraya
ELEVENLABS_BASE_URL=https://api.elevenlabs.io/v1
```

4. **Uygulamayı çalıştırın**
```bash
flutter run
```

Üretim derlemeleri için:
```bash
flutter build apk --release  # Android
flutter build ios --release  # iOS
```

---

## Proje Yapısı

```
elevenlabs_tts_app/
├── lib/
│   ├── config/
│   │   ├── theme.dart              # Uygulama tema yapılandırması
│   │   └── constants.dart          # Global sabitler ve yapılandırmalar
│   ├── models/
│   │   └── voice_model.dart        # Ses ve TTS istekleri için veri modelleri
│   ├── services/
│   │   ├── elevenlabs_api.dart     # API istemci implementasyonu
│   │   └── audio_service.dart      # Ses oynatma ve dosya yönetimi
│   ├── screens/
│   │   ├── home_screen.dart        # Ana metin girişi arayüzü
│   │   └── voice_selector_screen.dart  # Ses seçim arayüzü
│   ├── widgets/
│   │   ├── text_input_card.dart
│   │   ├── character_counter_card.dart
│   │   ├── voice_selector_button.dart
│   │   ├── generate_button.dart
│   │   └── voice_card.dart
│   ├── utils/
│   │   └── helpers.dart            # Yardımcı fonksiyonlar
│   └── main.dart                   # Uygulama giriş noktası
├── assets/
│   └── .env                        # API anahtarları (git'te izlenmiyor)
├── test/                           # Birim testleri
├── pubspec.yaml                    # Proje bağımlılıkları
└── README.md
```

---

## Kullanılan Teknolojiler

### Framework ve Dil
- **Flutter 3.0+**: Platformlar arası mobil framework
- **Dart 3.0+**: Programlama dili

### Temel Paketler

```yaml
dependencies:
  http: ^1.1.0                # HTTP istekleri için
  audioplayers: ^5.2.1        # Ses oynatma
  path_provider: ^2.1.1       # Dosya sistemi erişimi
  file_picker: ^6.1.1         # Dosya seçici dialog
  shared_preferences: ^2.2.2  # Yerel veri depolama
  flutter_dotenv: ^5.1.0      # Ortam değişkenleri yönetimi
```

### API Entegrasyonu
- **ElevenLabs Text-to-Speech API v1**
  - Base URL: `https://api.elevenlabs.io/v1`
  - Desteklenen Modeller: Flash v2.5, Multilingual v2, Turbo v2.5

---

## API Kullanımı

### Temel TTS İsteği

```dart
import 'package:elevenlabs_tts_app/services/elevenlabs_api.dart';
import 'package:elevenlabs_tts_app/models/voice_model.dart';

// API servisini başlat
final api = ElevenLabsAPI();
await api.initialize();

// TTS isteği oluştur
final request = TTSRequest(
  text: "Merhaba, bu bir test metnidir.",
  voiceId: "21m00Tcm4TlvDq8ikWAM", // Rachel (İngilizce)
  modelId: "eleven_flash_v2_5",
  stability: 0.5,
  similarityBoost: 0.75,
);

// Sesi oluştur
final audioBytes = await api.textToSpeech(request);

// Sesi oynat veya kaydet
await AudioService().playAudio(audioBytes);
```

### Ses Listesini Alma

```dart
// Tüm mevcut sesleri getir
final voices = await api.getVoices();

for (var voice in voices) {
  print('${voice.name} - ${voice.displayDescription}');
}
```

---

## Yapılandırma

### Ses Sentezi Parametreleri

| Parametre | Aralık | Varsayılan | Açıklama |
|-----------|--------|------------|----------|
| **stability** | 0.0 - 1.0 | 0.5 | Ses tutarlılığı (düşük = dinamik, yüksek = sabit) |
| **similarity_boost** | 0.0 - 1.0 | 0.75 | Orijinal sese benzerlik derecesi |
| **style** | 0.0 - 1.0 | 0.0 | Stil abartma seviyesi |
| **use_speaker_boost** | boolean | true | Konuşma netliğini artırır |

### Model Seçimi

```dart
// Hız öncelikli (düşük gecikme)
modelId: AppConstants.modelFlashV2  // "eleven_flash_v2_5"

// Kalite öncelikli (çoklu dil)
modelId: AppConstants.modelMultilingualV2  // "eleven_multilingual_v2"

// Ultra hız (gerçek zamanlı uygulamalar)
modelId: AppConstants.modelTurboV2  // "eleven_turbo_v2_5"
```

---

## Geliştirme

### Projeyi Geliştirme Modunda Çalıştırma

```bash
# Hot reload ile debug modu
flutter run

# Detaylı log çıktısı ile
flutter run -v

# Belirli bir cihazda
flutter devices
flutter run -d <device-id>
```

### Test Yazma

```bash
# Birim testlerini çalıştır
flutter test

# Test coverage raporu
flutter test --coverage
genhtml coverage/lcov.info -o coverage/html
```

### Kod Kalitesi

```bash
# Dart analyzer
flutter analyze

# Kod formatı kontrolü
dart format --set-exit-if-changed .

# Kod formatla
dart format .
```

---

## Performans Optimizasyonu

### Önerilen Ayarlar

- **Karakter Limiti**: 5,000 karakter (API limiti)
- **Önbellek Süresi**: 
  - Ses listesi: 60 dakika
  - Üretilen sesler: 24 saat
- **Ses Formatı**: MP3 (44.1kHz, 128kbps)

### Gecikme Azaltma

```dart
// Stream API kullanarak progresif oynatma
Stream<List<int>> audioStream = api.textToSpeechStream(request);
await for (var chunk in audioStream) {
  // Ses chunk'larını sırası ile oynat
  playChunk(chunk);
}
```

---

## Güvenlik

### Hassas Bilgilerin Korunması

- ✅ API anahtarları `.env` dosyasında saklanır
- ✅ `.env` dosyası `.gitignore`'a eklenmiştir
- ✅ HTTPS ile şifreli iletişim
- ✅ Kullanıcı verileri yerel olarak şifrelenir (SharedPreferences)

### Önerilen Güvenlik Uygulamaları

```dart
// API anahtarlarını kod içine asla yazmayın
❌ final apiKey = "sk_123456789"; // YANLIŞ

// .env dosyasını kullanın
✅ final apiKey = dotenv.env['ELEVENLABS_API_KEY']; // DOĞRU
```

---

## Dağıtım

### Android (APK/AAB)

```bash
# APK oluştur
flutter build apk --release

# App Bundle oluştur (Google Play için)
flutter build appbundle --release
```

### iOS (IPA)

```bash
# Xcode'da açın ve imzalayın
open ios/Runner.xcworkspace

# veya komut satırından
flutter build ios --release
```

---

## Sorun Giderme

### Yaygın Hatalar

**1. API Anahtarı Geçersiz**
```
Çözüm: .env dosyasındaki API anahtarını kontrol edin
https://elevenlabs.io/app/settings/api-keys adresinden yeni anahtar alın
```

**2. Ses Oynatılamıyor**
```
Çözüm: 
- İnternet bağlantısını kontrol edin
- Cihaz ses ayarlarını kontrol edin
- audioplayers paketinin doğru yüklendiğinden emin olun
```

**3. Build Hatası (Android)**
```bash
cd android
./gradlew clean
cd ..
flutter clean
flutter pub get
flutter run
```

**4. iOS Pod Hatası**
```bash
cd ios
pod deintegrate
pod install
cd ..
flutter clean
flutter run
```

---

## Katkıda Bulunma

Katkılarınızı memnuniyetle karşılıyoruz! Lütfen aşağıdaki adımları izleyin:

1. Bu depoyu fork edin
2. Yeni bir özellik dalı oluşturun (`git checkout -b feature/YeniOzellik`)
3. Değişikliklerinizi commit edin (`git commit -m 'Yeni özellik: XYZ'`)
4. Dalınıza push yapın (`git push origin feature/YeniOzellik`)
5. Bir Pull Request açın

### Kod Stil Rehberi

- [Effective Dart](https://dart.dev/guides/language/effective-dart) kurallarına uyun
- Anlamlı commit mesajları yazın
- Yeni özellikler için birim testleri ekleyin
- Dokümantasyonu güncel tutun

---

## Lisans

Bu proje MIT Lisansı altında lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasına bakın.

---

## İletişim

- **Proje Sahibi**: [İsminiz]
- **Email**: email@example.com
- **GitHub**: [@kullaniciadi](https://github.com/kullaniciadi)

---

## Teşekkürler

- [ElevenLabs](https://elevenlabs.io) - Ses sentezi API'si
- [Flutter](https://flutter.dev) - Mobil framework
- [Flutter Community](https://flutter.dev/community) - Açık kaynak paketler

---

<div align="center">

**⭐ Projeyi beğendiyseniz yıldız vermeyi unutmayın!**

Made with ❤️ and Flutter

</div>
