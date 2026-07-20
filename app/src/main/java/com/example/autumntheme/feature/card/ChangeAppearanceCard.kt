package com.example.autumntheme.feature.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.autumntheme.R
import com.example.autumntheme.ui.theme.AmberGold
import com.example.autumntheme.ui.theme.BorderTan
import com.example.autumntheme.ui.theme.CherryBlossomPink
import com.example.autumntheme.ui.theme.CherryBlushPink
import com.example.autumntheme.ui.theme.CherryDeepPink
import com.example.autumntheme.ui.theme.CherryVibrantPink
import com.example.autumntheme.ui.theme.DeepBrown
import com.example.autumntheme.ui.theme.PumpkinOrange
import com.example.autumntheme.ui.theme.WarmCream
import com.example.autumntheme.ui.theme.DenimBlue
import com.example.autumntheme.ui.theme.SoftBlueGray
import com.example.autumntheme.ui.theme.DeepNavy
import com.example.autumntheme.ui.theme.SnowBackground
import com.example.autumntheme.ui.theme.MatchaBackground
import com.example.autumntheme.ui.theme.MatchaSage
import com.example.autumntheme.ui.theme.MatchaPrimary
import com.example.autumntheme.ui.theme.MatchaDarkForest
import com.example.autumntheme.ui.theme.LagoonDeepTeal
import com.example.autumntheme.ui.theme.LagoonMint
import com.example.autumntheme.ui.theme.LagoonMist
import com.example.autumntheme.ui.theme.LagoonSage
import com.example.autumntheme.ui.theme.LagoonTeal
import com.example.autumntheme.ui.theme.AcledaBlueGray
import com.example.autumntheme.ui.theme.AcledaNavy
import com.example.autumntheme.ui.theme.AcledaRoyalNavy
import com.example.autumntheme.ui.theme.FrostedBlue
import com.example.autumntheme.ui.theme.HarborBlue
import com.example.autumntheme.ui.theme.SapphireBlue
import com.example.autumntheme.ui.theme.RomdoulCream
import com.example.autumntheme.ui.theme.RomdoulDeepFoliage
import com.example.autumntheme.ui.theme.RomdoulGreen
import com.example.autumntheme.ui.theme.RomdoulOrange
import com.example.autumntheme.ui.theme.RomdoulYellow

data class CardTheme(
    val name: String,
    val backgroundRes: Int,
    val buttonColor: Color,
    val buttonTextColor: Color,
    val iconBorderColor: Color = Color.Transparent,
    val iconCornerRadius: Dp = 6.dp,
    val themeImages: List<Int>,
    val sheetContainerColor: Color,
    val sheetContentColor: Color,
    val primaryTextColor: Color,
    val cardBackgroundColor: Color,
    val secondaryTextColor: Color,
    val leafImageRes: Int? = null,
    val icWallet: Int,
    val icScanner: Int,
    val icTransfer: Int,
    val icCard: Int,
    val icDeposit: Int,
    val icLoan: Int,
    val icPayment: Int,
    val icTopup: Int,
    val icQuickCash: Int,
    val icSchool: Int,
    val icDepartment: Int,
    val icExchange: Int,
    val previewImageRes: Int,
    val useGlassEffect: Boolean = true,
    val showBalanceVisibilityIcon: Boolean = true,
    val logoRes: Int = R.drawable.img_def_ac,
    val useRomdoulMotif: Boolean = false,
    val backgroundScrimColor: Color = Color.Transparent
)

val  AutumnTheme = CardTheme(
    name = "Autumn",
    backgroundRes = R.drawable.img_autumn_background,
    buttonColor = PumpkinOrange,
    buttonTextColor = Color.White,
    iconBorderColor = AmberGold,
    iconCornerRadius = 12.dp,
    themeImages = listOf(
        R.drawable.img_def_banner1,
        R.drawable.img_def_banner4,
        R.drawable.img_def_banner1,
        R.drawable.img_def_banner4,
    ),
    sheetContainerColor = DeepBrown,
    sheetContentColor = BorderTan,
    primaryTextColor = DeepBrown,
    cardBackgroundColor = DeepBrown,
    secondaryTextColor = WarmCream,
    leafImageRes = R.drawable.img_autumn_maple_leaf,
    icWallet = R.drawable.ic_autumn_wallet,
    icScanner = R.drawable.ic_autumn_scanner,
    icTransfer = R.drawable.ic_autumn_transfer,
    icCard = R.drawable.ic_autumn_card,
    icDeposit = R.drawable.ic_autumn_deposit,
    icLoan = R.drawable.ic_autumn_loan,
    icPayment = R.drawable.ic_autumn_payment,
    icTopup = R.drawable.ic_autumn_topup,
    icQuickCash = R.drawable.ic_autumn_quickcash,
    icSchool = R.drawable.ic_autumn_school,
    icDepartment = R.drawable.ic_autumn_department,
    icExchange = R.drawable.ic_autumn_exchange,
    previewImageRes = R.drawable.img_theme_preview_autumn,
    useGlassEffect = false
    
)

val CherryBlossomTheme = CardTheme(
    name = "Cherry Blossom",
    backgroundRes = R.drawable.img_blossom_background,
    buttonColor = CherryVibrantPink,
    buttonTextColor = Color.White,
    iconBorderColor = CherryBlossomPink,
    iconCornerRadius = 18.dp,
    themeImages = listOf(
        R.drawable.img_def_theme5,
        R.drawable.img_def_theme1,
        R.drawable.img_def_theme2,
        R.drawable.img_def_theme3,
    ),
    sheetContainerColor = CherryDeepPink,
    sheetContentColor = CherryVibrantPink,
    primaryTextColor = CherryDeepPink,
    cardBackgroundColor = CherryVibrantPink,
    secondaryTextColor = CherryBlushPink,
    leafImageRes = R.drawable.img_blossom_sakura_leaf,
    icWallet = R.drawable.ic_blossom_wallet,
    icScanner = R.drawable.ic_blossom_scanner,
    icTransfer = R.drawable.ic_blossom_transfer,
    icCard = R.drawable.ic_bollom_card,
    icDeposit = R.drawable.ic_blossom_deposit,
    icLoan = R.drawable.ic_blossom_loan,
    icPayment = R.drawable.ic_blossom_payment,
    icTopup = R.drawable.ic_blossom_topup,
    icQuickCash = R.drawable.ic_blossom_quickcash,
    icSchool = R.drawable.ic_blossom_school,
    icDepartment = R.drawable.ic_bollom_location,
    icExchange = R.drawable.ic_blossom_exhange,
    previewImageRes = R.drawable.img_theme_preview_cheeryblossom
)
val FrostedTheme = CardTheme(
    name = "Frosted",
    backgroundRes = R.drawable.img_denim_background,
    buttonColor = DenimBlue,
    buttonTextColor = Color.White,
    iconBorderColor = SoftBlueGray,
    iconCornerRadius = 14.dp,
    themeImages = listOf(
        R.drawable.img_def_theme1,
        R.drawable.img_def_theme2,
        R.drawable.img_def_theme3,
        R.drawable.img_def_theme4,
    ),
    sheetContainerColor = DeepNavy,
    sheetContentColor = SoftBlueGray,
    primaryTextColor = DeepNavy,
    cardBackgroundColor = DeepNavy,
    secondaryTextColor = SnowBackground,
    leafImageRes = R.drawable.ic_snowflake,
    icWallet = R.drawable.ic_professional_wallet,
    icScanner = R.drawable.ic_professional_scanner,
    icTransfer = R.drawable.ic_professional_transfer,
    icCard = R.drawable.ic_professional_card,
    icDeposit = R.drawable.ic_professional_deposit,
    icLoan = R.drawable.ic_professional_loan,
    icPayment = R.drawable.ic_professional_payment,
    icTopup = R.drawable.ic_professional_topup,
    icQuickCash = R.drawable.ic_professional_quickcash,
    icSchool = R.drawable.ic_professional_school,
    icDepartment = R.drawable.ic_professional_department,
    icExchange = R.drawable.ic_professional_exchange,
    previewImageRes = R.drawable.img_theme_preview_denim
)

val MatchaTheme = CardTheme(
    name = "Matcha",
    backgroundRes = R.drawable.img_matcha_background,
    buttonColor = MatchaPrimary,
    buttonTextColor = Color.White,
    iconBorderColor = MatchaSage,
    iconCornerRadius = 14.dp,
    themeImages = listOf(
        R.drawable.img_def_theme1,
        R.drawable.img_def_theme2,
        R.drawable.img_def_theme3,
        R.drawable.img_def_theme4,
    ),

    sheetContainerColor = MatchaDarkForest,
    sheetContentColor = MatchaSage,
    primaryTextColor = MatchaDarkForest,
    cardBackgroundColor = MatchaDarkForest,
    secondaryTextColor = MatchaBackground,
    leafImageRes = R.drawable.img_matcha_leaf,
    icWallet = R.drawable.ic_professional_wallet,
    icScanner = R.drawable.ic_professional_scanner,
    icTransfer = R.drawable.ic_professional_transfer,
    icCard = R.drawable.ic_professional_card,
    icDeposit = R.drawable.ic_professional_deposit,
    icLoan = R.drawable.ic_professional_loan,
    icPayment = R.drawable.ic_professional_payment,
    icTopup = R.drawable.ic_professional_topup,
    icQuickCash = R.drawable.ic_professional_quickcash,
    icSchool = R.drawable.ic_professional_school,
    icDepartment = R.drawable.ic_professional_department,
    icExchange = R.drawable.ic_professional_exchange,
//    previewImageRes = R.drawable.img_theme_preview_denim
//    icWallet = R.drawable.ic_matcha_wallet,
//    icScanner = R.drawable.ic_matcha_scanner,
//    icTransfer = R.drawable.ic_matcha_transfer,
//    icCard = R.drawable.ic_matcha_card,
//    icDeposit = R.drawable.ic_matcha_deposit,
//    icLoan = R.drawable.ic_matcha_loan,
//    icPayment = R.drawable.ic_matcha_payment,
//    icTopup = R.drawable.ic_matcha_topup,
//    icQuickCash = R.drawable.ic_matcha_quickcash,
//    icSchool = R.drawable.ic_matcha_school,
//    icDepartment = R.drawable.ic_matcha_department,
//    icExchange = R.drawable.ic_matcha_exchange,
    previewImageRes = R.drawable.img_theme_preview_macha
)

val LagoonTheme = CardTheme(
    name = "Lagoon",
    backgroundRes = R.drawable.img_lagoon_background,
    buttonColor = LagoonDeepTeal,
    buttonTextColor = Color.White,
    iconBorderColor = LagoonMint,
    iconCornerRadius = 16.dp,
    themeImages = listOf(
        R.drawable.img_def_theme1,
        R.drawable.img_def_theme2,
        R.drawable.img_def_theme3,
        R.drawable.img_def_theme4,
    ),
    sheetContainerColor = LagoonDeepTeal,
    sheetContentColor = LagoonSage,
    primaryTextColor = LagoonDeepTeal,
    cardBackgroundColor = LagoonTeal,
    secondaryTextColor = LagoonMist,
    leafImageRes = R.drawable.ic_lagoon_leaf,
    icWallet = R.drawable.ic_professional_wallet,
    icScanner = R.drawable.ic_professional_scanner,
    icTransfer = R.drawable.ic_professional_transfer,
    icCard = R.drawable.ic_professional_card,
    icDeposit = R.drawable.ic_professional_deposit,
    icLoan = R.drawable.ic_professional_loan,
    icPayment = R.drawable.ic_professional_payment,
    icTopup = R.drawable.ic_professional_topup,
    icQuickCash = R.drawable.ic_professional_quickcash,
    icSchool = R.drawable.ic_professional_school,
    icDepartment = R.drawable.ic_professional_department,
    icExchange = R.drawable.ic_professional_exchange,
    previewImageRes = R.drawable.img_lagoon_background
)

val GlassTheme = CardTheme(
    name = "Glass",
    backgroundRes = R.drawable.img_glass_background,
    buttonColor = Color(0x33FFFFFF),
    buttonTextColor = Color.White,
    iconBorderColor = Color(0x66FFFFFF),
    iconCornerRadius = 16.dp,
    themeImages = listOf(
        R.drawable.img_def_theme1,
        R.drawable.img_def_theme2,
        R.drawable.img_def_theme3,
        R.drawable.img_def_theme4
    ),
    sheetContainerColor = Color(0xCC111111),
    sheetContentColor = Color(0x33FFFFFF),
    primaryTextColor = Color.White,
    cardBackgroundColor = Color(0x22FFFFFF),
    secondaryTextColor = Color(0xCCFFFFFF),
    backgroundScrimColor = Color(0x66081424),
    leafImageRes = R.drawable.img_autumn_maple_leaf,
    icWallet = R.drawable.ic_glass_wallet,
    icScanner = R.drawable.ic_glass_scanner,
    icTransfer = R.drawable.ic_glass_transfer,
    icCard = R.drawable.ic_glass_card,
    icDeposit = R.drawable.ic_glass_deposit,
    icLoan = R.drawable.ic_glass_loan,
    icPayment = R.drawable.ic_glass_payment,
    icTopup = R.drawable.ic_glass_topup,
    icQuickCash = R.drawable.ic_glass_quickcash,
    icSchool = R.drawable.ic_glass_school,
    icDepartment = R.drawable.ic_glass_department,
    icExchange = R.drawable.ic_glass_exchange,
    previewImageRes = R.drawable.img_theme_preview_glass
)

val ProfessionalTheme = CardTheme(
    name = "Professional",
    backgroundRes = R.drawable.img_professional_background,
    buttonColor = Color(0xFF1E3A8A),
    buttonTextColor = Color.White,
    iconBorderColor = Color(0xFF3B82F6),
    iconCornerRadius = 14.dp,
    themeImages = listOf(
        R.drawable.img_def_theme1,
        R.drawable.img_def_theme2,
        R.drawable.img_def_theme3,
        R.drawable.img_def_theme4,
    ),
    sheetContainerColor = Color(0xFF0F172A),
    sheetContentColor = Color(0xFF334155),
    primaryTextColor = Color.White,
    cardBackgroundColor = Color(0xFF1E293B),
    secondaryTextColor = Color(0xFFF8FAFC),
    leafImageRes = R.drawable.ic_professional_wallet,
    icWallet = R.drawable.ic_professional_wallet,
    icScanner = R.drawable.ic_professional_scanner,
    icTransfer = R.drawable.ic_professional_transfer,
    icCard = R.drawable.ic_professional_card,
    icDeposit = R.drawable.ic_professional_deposit,
    icLoan = R.drawable.ic_professional_loan,
    icPayment = R.drawable.ic_professional_payment,
    icTopup = R.drawable.ic_professional_topup,
    icQuickCash = R.drawable.ic_professional_quickcash,
    icSchool = R.drawable.ic_professional_school,
    icDepartment = R.drawable.ic_professional_department,
    icExchange = R.drawable.ic_professional_exchange,
    previewImageRes = R.drawable.img_professional_background
)

val SapphireTheme = CardTheme(
    name = "Sapphire",
    backgroundRes = R.drawable.bg_sapphire_solid,
    buttonColor = Color(0xFF5CA8E8),
    buttonTextColor = Color.White,
    iconBorderColor = Color(0xFF86C8F5),
    iconCornerRadius = 18.dp,
    themeImages = listOf(R.drawable.img_def_theme1, R.drawable.img_def_theme2, R.drawable.img_def_theme3, R.drawable.img_def_theme4),
    sheetContainerColor = Color(0xFF112B56),
    sheetContentColor = Color(0xFF244B7A),
    primaryTextColor = Color.White,
    cardBackgroundColor = Color(0xFF3D699B),
    secondaryTextColor = Color(0xFFF5FAFF),
    leafImageRes = R.drawable.ic_professional_wallet,
    icWallet = R.drawable.ic_professional_wallet,
    icScanner = R.drawable.ic_professional_scanner,
    icTransfer = R.drawable.ic_professional_transfer,
    icCard = R.drawable.ic_professional_card,
    icDeposit = R.drawable.ic_professional_deposit,
    icLoan = R.drawable.ic_professional_loan,
    icPayment = R.drawable.ic_professional_payment,
    icTopup = R.drawable.ic_professional_topup,
    icQuickCash = R.drawable.ic_professional_quickcash,
    icSchool = R.drawable.ic_professional_school,
    icDepartment = R.drawable.ic_professional_department,
    icExchange = R.drawable.ic_professional_exchange,
    previewImageRes = R.drawable.bg_sapphire_solid,
    useGlassEffect = false
)

val HarborTheme = CardTheme(
    name = "Harbor",
    backgroundRes = R.drawable.bg_harbor_solid,
    buttonColor = Color(0xFF7EB8D4),
    buttonTextColor = Color.White,
    iconBorderColor = Color(0xFFB6D8E6),
    iconCornerRadius = 18.dp,
    themeImages = listOf(R.drawable.img_def_theme1, R.drawable.img_def_theme2, R.drawable.img_def_theme3, R.drawable.img_def_theme4),
    sheetContainerColor = Color(0xFF133154),
    sheetContentColor = Color(0xFF315C7D),
    primaryTextColor = Color.White,
    cardBackgroundColor = Color(0xFF457595),
    secondaryTextColor = Color(0xFFF7FBFD),
    leafImageRes = R.drawable.ic_professional_wallet,
    icWallet = R.drawable.ic_professional_wallet,
    icScanner = R.drawable.ic_professional_scanner,
    icTransfer = R.drawable.ic_professional_transfer,
    icCard = R.drawable.ic_professional_card,
    icDeposit = R.drawable.ic_professional_deposit,
    icLoan = R.drawable.ic_professional_loan,
    icPayment = R.drawable.ic_professional_payment,
    icTopup = R.drawable.ic_professional_topup,
    icQuickCash = R.drawable.ic_professional_quickcash,
    icSchool = R.drawable.ic_professional_school,
    icDepartment = R.drawable.ic_professional_department,
    icExchange = R.drawable.ic_professional_exchange,
    previewImageRes = R.drawable.bg_harbor_solid
)

val MidnightTheme = CardTheme(
    name = "Midnight",
    backgroundRes = R.drawable.bg_midnight_solid,
    buttonColor = Color(0xFF86B9E8),
    buttonTextColor = AcledaNavy,
    iconBorderColor = Color(0xFF5F9FDB),
    iconCornerRadius = 18.dp,
    themeImages = listOf(R.drawable.img_def_theme1, R.drawable.img_def_theme2, R.drawable.img_def_theme3, R.drawable.img_def_theme4),
    sheetContainerColor = Color(0xFF0C1933),
    sheetContentColor = Color(0xFF1C355E),
    primaryTextColor = Color.White,
    cardBackgroundColor = Color(0xFF15294A),
    secondaryTextColor = Color(0xFFF2F7FF),
    leafImageRes = null,
    icWallet = R.drawable.ic_professional_wallet,
    icScanner = R.drawable.ic_professional_scanner,
    icTransfer = R.drawable.ic_professional_transfer,
    icCard = R.drawable.ic_professional_card,
    icDeposit = R.drawable.ic_professional_deposit,
    icLoan = R.drawable.ic_professional_loan,
    icPayment = R.drawable.ic_professional_payment,
    icTopup = R.drawable.ic_professional_topup,
    icQuickCash = R.drawable.ic_professional_quickcash,
    icSchool = R.drawable.ic_professional_school,
    icDepartment = R.drawable.ic_professional_department,
    icExchange = R.drawable.ic_professional_exchange,
    previewImageRes = R.drawable.bg_midnight_solid,
    useGlassEffect = false,
    showBalanceVisibilityIcon = false,
    logoRes = R.drawable.img_ac_logo_white
)

val RomdoulTheme = CardTheme(
    name = "Romdoul",
    backgroundRes = R.drawable.img_romdoul_background,
    buttonColor = RomdoulOrange,
    buttonTextColor = Color.White,
    iconBorderColor = RomdoulYellow,
    iconCornerRadius = 18.dp,
    themeImages = listOf(R.drawable.img_def_theme1, R.drawable.img_def_theme2, R.drawable.img_def_theme3, R.drawable.img_def_theme4),
    sheetContainerColor = RomdoulDeepFoliage,
    sheetContentColor = RomdoulGreen,
    primaryTextColor = RomdoulDeepFoliage,
    cardBackgroundColor = RomdoulDeepFoliage,
    secondaryTextColor = RomdoulCream,
    leafImageRes = R.drawable.img_romdoul_flower,
    icWallet = R.drawable.ic_romdoul_wallet,
    icScanner = R.drawable.ic_romdoul_scanner,
    icTransfer = R.drawable.ic_romdoul_transfer,
    icCard = R.drawable.ic_romdoul_card,
    icDeposit = R.drawable.ic_romdoul_deposit,
    icLoan = R.drawable.ic_romdoul_loan,
    icPayment = R.drawable.ic_romdoul_payment,
    icTopup = R.drawable.ic_romdoul_topup,
    icQuickCash = R.drawable.ic_romdoul_quickcash,
    icSchool = R.drawable.ic_romdoul_school,
    icDepartment = R.drawable.ic_romdoul_department,
    icExchange = R.drawable.ic_romdoul_exchange,
    previewImageRes = R.drawable.img_romdoul_background,
    useRomdoulMotif = true,
    useGlassEffect = false
)

val ProfessionalTheme1 = CardTheme(
    name = "Tester Professional",
    backgroundRes = R.drawable.img_professional_background,
    buttonColor = Color(0xFF1E3A8A),
    buttonTextColor = Color.White,
    iconBorderColor = Color(0xFF3B82F6),
    iconCornerRadius = 14.dp,
    themeImages = listOf(
        R.drawable.img_def_theme1,
        R.drawable.img_def_theme2,
        R.drawable.img_def_theme3,
        R.drawable.img_def_theme4,
    ),
    sheetContainerColor = Color(0xFF0F172A),
    sheetContentColor = Color(0xFF334155),
    primaryTextColor = Color.White,
    cardBackgroundColor = Color(0xFF1E293B),
    secondaryTextColor = Color(0xFFF8FAFC),
    leafImageRes = R.drawable.ic_professional_wallet,
    icWallet = R.drawable.ic_professional_wallet,
    icScanner = R.drawable.ic_professional_scanner,
    icTransfer = R.drawable.ic_professional_transfer,
    icCard = R.drawable.ic_professional_card,
    icDeposit = R.drawable.ic_professional_deposit,
    icLoan = R.drawable.ic_professional_loan,
    icPayment = R.drawable.ic_professional_payment,
    icTopup = R.drawable.ic_professional_topup,
    icQuickCash = R.drawable.ic_professional_quickcash,
    icSchool = R.drawable.ic_professional_school,
    icDepartment = R.drawable.ic_professional_department,
    icExchange = R.drawable.ic_professional_exchange,
    previewImageRes = R.drawable.img_professional_background
)

val MonochromeTheme = CardTheme(
    name = "Monochrome",
    backgroundRes = R.drawable.img_monochrome_background,
    buttonColor = Color(0xFFE2E8F0),
    buttonTextColor = Color(0xFF0F172A),
    iconBorderColor = Color(0xFF64748B),
    iconCornerRadius = 14.dp,
    themeImages = listOf(
        R.drawable.img_def_theme1,
        R.drawable.img_def_theme2,
        R.drawable.img_def_theme3,
        R.drawable.img_def_theme4,
    ),
    sheetContainerColor = Color(0xFF0F172A),
    sheetContentColor = Color(0xFF475569),
    primaryTextColor = Color.White,
    cardBackgroundColor = Color(0xFF334155),
    secondaryTextColor = Color(0xFFF1F5F9),
    leafImageRes = R.drawable.ic_professional_wallet,
    icWallet = R.drawable.ic_professional_wallet,
    icScanner = R.drawable.ic_professional_scanner,
    icTransfer = R.drawable.ic_professional_transfer,
    icCard = R.drawable.ic_professional_card,
    icDeposit = R.drawable.ic_professional_deposit,
    icLoan = R.drawable.ic_professional_loan,
    icPayment = R.drawable.ic_professional_payment,
    icTopup = R.drawable.ic_professional_topup,
    icQuickCash = R.drawable.ic_professional_quickcash,
    icSchool = R.drawable.ic_professional_school,
    icDepartment = R.drawable.ic_professional_department,
    icExchange = R.drawable.ic_professional_exchange,
    previewImageRes = R.drawable.img_monochrome_background
)

val GoldTheme = CardTheme(
    name = "Gold Premium",
    backgroundRes = R.drawable.img_gold_background,
    buttonColor = Color(0xFFE5C158),
    buttonTextColor = Color(0xFF1A1A1A),
    iconBorderColor = Color(0xFFC5A059),
    iconCornerRadius = 14.dp,
    themeImages = listOf(
        R.drawable.img_def_theme1,
        R.drawable.img_def_theme2,
        R.drawable.img_def_theme3,
        R.drawable.img_def_theme4,
    ),
    sheetContainerColor = Color(0xFF1E1A10),
    sheetContentColor = Color(0xFFE5C158),
    primaryTextColor = Color(0xFFE5C158),
    cardBackgroundColor = Color(0xFF2C2415),
    secondaryTextColor = Color(0xFFFFF2D0),
    leafImageRes = R.drawable.ic_professional_wallet,
    icWallet = R.drawable.ic_professional_wallet,
    icScanner = R.drawable.ic_professional_scanner,
    icTransfer = R.drawable.ic_professional_transfer,
    icCard = R.drawable.ic_professional_card,
    icDeposit = R.drawable.ic_professional_deposit,
    icLoan = R.drawable.ic_professional_loan,
    icPayment = R.drawable.ic_professional_payment,
    icTopup = R.drawable.ic_professional_topup,
    icQuickCash = R.drawable.ic_professional_quickcash,
    icSchool = R.drawable.ic_professional_school,
    icDepartment = R.drawable.ic_professional_department,
    icExchange = R.drawable.ic_professional_exchange,
    previewImageRes = R.drawable.img_gold_background
)

val HalloweenTheme = CardTheme(
    name = "Halloween",
    backgroundRes = R.drawable.img_halloween_background,
    buttonColor = Color(0xFFD97706),
    buttonTextColor = Color.White,
    iconBorderColor = Color(0xFF7C3AED),
    iconCornerRadius = 14.dp,
    themeImages = listOf(
        R.drawable.img_def_theme1,
        R.drawable.img_def_theme2,
        R.drawable.img_def_theme3,
        R.drawable.img_def_theme4,
    ),
    sheetContainerColor = Color(0xFF111827),
    sheetContentColor = Color(0xFFD97706),
    primaryTextColor = Color.White,
    cardBackgroundColor = Color(0xFF1F2937),
    secondaryTextColor = Color(0xFFF3F4F6),
    leafImageRes = R.drawable.ic_professional_wallet,
    icWallet = R.drawable.ic_professional_wallet,
    icScanner = R.drawable.ic_professional_scanner,
    icTransfer = R.drawable.ic_professional_transfer,
    icCard = R.drawable.ic_professional_card,
    icDeposit = R.drawable.ic_professional_deposit,
    icLoan = R.drawable.ic_professional_loan,
    icPayment = R.drawable.ic_professional_payment,
    icTopup = R.drawable.ic_professional_topup,
    icQuickCash = R.drawable.ic_professional_quickcash,
    icSchool = R.drawable.ic_professional_school,
    icDepartment = R.drawable.ic_professional_department,
    icExchange = R.drawable.ic_professional_exchange,
    previewImageRes = R.drawable.img_halloween_background
)

val AllThemes = listOf(AutumnTheme, CherryBlossomTheme, MatchaTheme, LagoonTheme, ProfessionalTheme, SapphireTheme, HarborTheme, MidnightTheme, RomdoulTheme, MonochromeTheme, GoldTheme)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenshotThemeCard(
    currentTheme: CardTheme,
    availableThemes: List<CardTheme> = AllThemes,
    onThemeSelected: (CardTheme) -> Unit,
    fontScale: Float = 1.0f,
    onFontScaleChanged: (Float) -> Unit = {}
) {
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val haptic = LocalHapticFeedback.current

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(18.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(18.dp))
        ) {
            AsyncImage(
                model = currentTheme.backgroundRes,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Row(
                modifier = Modifier.matchParentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 12.dp, end = 2.dp)
                        .clipToBounds()
                ) {
                    currentTheme.themeImages.forEachIndexed { index, imageRes ->
                        val yOffset = when (index) {
                            0 -> 50.dp
                            1 -> 65.dp
                            2 -> 35.dp
                            3 -> 65.dp
                            else -> 0.dp
                        }

                        Box(
                            modifier = Modifier
                                .width(52.dp)
                                .fillMaxHeight()
                        ) {
                            AsyncImage(
                                model = imageRes,
                                contentDescription = "Icon ${index + 1}",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                                    .offset(y = yOffset)
                                    .clip(RoundedCornerShape(currentTheme.iconCornerRadius))
                                    .border(
                                        width = 1.dp,
                                        color = currentTheme.iconBorderColor,
                                        shape = RoundedCornerShape(currentTheme.iconCornerRadius)
                                    )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 20.dp)
                        .width(110.dp)
                        .height(36.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(18.dp))
                        .clip(RoundedCornerShape(18.dp))
                        .background(color = PumpkinOrange)
                        .clickable { showSheet = true },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Customize",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                          color = WarmCream
                    )
                }

                if (showSheet) {
                    ModalBottomSheet(
                        onDismissRequest = { showSheet = false },
                        sheetState = sheetState,
                        containerColor = currentTheme.sheetContainerColor,
                        dragHandle = { BottomSheetDefaults.DragHandle() }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 4.dp)
                                .navigationBarsPadding()
                        ) {
                            Text(
                                text = "Select Theme",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(currentTheme.sheetContentColor, RoundedCornerShape(16.dp))
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(availableThemes) { themeOption ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                         modifier = Modifier.clickable { 
                                             haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                             onThemeSelected(themeOption)
                                             showSheet = false
                                         }
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .width(100.dp)
                                                .aspectRatio(9f / 20f)
                                                .clip(RoundedCornerShape(12.dp))
                                                .border(
                                                    width = if (currentTheme.name == themeOption.name) 3.dp else 1.dp,
                                                    color = if (currentTheme.name == themeOption.name) Color.White else Color.Transparent,
                                                    shape = RoundedCornerShape(12.dp)
                                                )
                                        ) {
                                            AsyncImage(
                                                model = themeOption.previewImageRes,
                                                contentDescription = themeOption.name,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }
                                        Text(
                                            text = themeOption.name,
                                            color = if (currentTheme.sheetContentColor.luminance() > 0.5f) currentTheme.primaryTextColor else Color.White,
                                            fontSize = 12.sp,
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                    }
                                }
                            }
                            Spacer(Modifier.height(20.dp))
                            Text(
                                text = "Text Size",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(currentTheme.sheetContentColor, RoundedCornerShape(16.dp))
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "A", 
                                    fontSize = 12.sp, 
                                    color = if (currentTheme.sheetContentColor.luminance() > 0.5f) currentTheme.primaryTextColor else Color.White
                                )
                                Slider(
                                    value = fontScale,
                                    onValueChange = { onFontScaleChanged(it) },
                                    valueRange = 0.85f..1.3f,
                                    steps = 2,
                                    modifier = Modifier.weight(1f).padding(horizontal = 12.dp),
                                    colors = SliderDefaults.colors(
                                        activeTrackColor = currentTheme.buttonColor,
                                        inactiveTrackColor = (if (currentTheme.sheetContentColor.luminance() > 0.5f) currentTheme.primaryTextColor else Color.White).copy(alpha = 0.24f),
                                        thumbColor = currentTheme.buttonColor,
                                        activeTickColor = Color.Transparent,
                                        inactiveTickColor = Color.Transparent
                                    )
                                )
                                Text(
                                    text = "A", 
                                    fontSize = 20.sp, 
                                    fontWeight = FontWeight.Bold, 
                                    color = if (currentTheme.sheetContentColor.luminance() > 0.5f) currentTheme.primaryTextColor else Color.White
                                )
                            }
                            Spacer(Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeSwitchingScreen() {
    var currentTheme by remember { mutableStateOf(AutumnTheme) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0B0B))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ScreenshotThemeCard(
            currentTheme = currentTheme,
            onThemeSelected = { newTheme -> currentTheme = newTheme }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Tap 'Customize' to change themes inside the Sheet",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomThemeCardPreview() {
    ThemeSwitchingScreen()
}
