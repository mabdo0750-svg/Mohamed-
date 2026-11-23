package com.example.model

import com.example.R

object DaraRepository {

    const val PROJECT_NAME = "دارة السويس"
    const val DEVELOPER_ENTITY = "صندوق التنمية الحضرية – محافظة السويس"
    const val SALES_DIRECTOR_NAME = "محمد عبده"
    const val SALES_DIRECTOR_TITLE = "مدير مبيعات صندوق التنمية الحضرية بمحافظة السويس"
    const val SALES_OFFICE_ADDRESS = "مكتب المبيعات داخل كمباوند دارة حياة – شارع إبراهيم نافع – عمارة 145 – الدور الأول"
    const val SALES_OFFICE_HOURS = "في خدمتك على مدار اليوم"
    const val PROJECT_TAGLINE = "مشروع صندوق التنمية الحضرية بالسويس • مباني منتهية بالكامل ووحدات متشطبة وجاهزة"
    const val HOTLINE_NUMBER = "15444"
    const val SALES_MOBILE_NUMBER = "01033389338"

    val constructionOverviewCompleted = "المشروع جاهز بالكامل: جميع المباني، اللاندسكيب، الطرق الداخلية، ومنظومة الأمن والإدارة مكتملة، والوحدات متشطبة وجاهزة."

    val cooperatingBankNames = listOf(
        "بنك مصر",
        "بنك الإسكان والتعمير",
        "بنك CIB",
        "بنك QNB",
        "البنك الأهلي المصري",
        "بنك القاهرة",
        "بنك تنمية الصادرات",
        "بنك تنمية الصناعات"
    )

    val bankPartners: List<BankPartner> = listOf(
        BankPartner(
            id = "banque_misr",
            bankNameAr = "بنك مصر",
            representativeNameAr = "أ/ مينا",
            phoneNumbers = listOf("01222293182")
        ),
        BankPartner(
            id = "qnb",
            bankNameAr = "بنك QNB",
            representativeNameAr = "أ/ محمد رزق",
            phoneNumbers = listOf("01022369361")
        ),
        BankPartner(
            id = "cib_ahmed",
            bankNameAr = "بنك CIB",
            representativeNameAr = "أ/ أحمد نبيل",
            phoneNumbers = listOf("01023699476")
        ),
        BankPartner(
            id = "cib_mohamed",
            bankNameAr = "بنك CIB",
            representativeNameAr = "أ/ محمد عبد العليم",
            phoneNumbers = listOf("01114452396")
        ),
        BankPartner(
            id = "nbe",
            bankNameAr = "البنك الأهلي المصري",
            representativeNameAr = "فريق التمويل العقاري",
            phoneNumbers = listOf("01000158595", "01008074515")
        ),
        BankPartner(
            id = "ebe",
            bankNameAr = "بنك تنمية الصادرات",
            representativeNameAr = "أ/ إبراهيم",
            phoneNumbers = listOf("01003774745")
        ),
        BankPartner(
            id = "hdb",
            bankNameAr = "بنك الإسكان والتعمير",
            representativeNameAr = "أ/ محمد",
            phoneNumbers = listOf("01018666992")
        ),
        BankPartner(
            id = "idb",
            bankNameAr = "بنك تنمية الصناعات",
            representativeNameAr = "أ/ فادي",
            phoneNumbers = listOf("01222411187")
        ),
        BankPartner(
            id = "banque_du_caire",
            bankNameAr = "بنك القاهرة",
            representativeNameAr = null,
            phoneNumbers = emptyList(),
            noteAr = "متاح التمويل العقاري — يتم الربط عبر مكتب المبيعات أو الخط الساخن 15444"
        )
    )

    val compounds: List<Compound> = listOf(
        Compound(
            id = "kayan",
            nameAr = "كمباوند دارة كيان",
            englishCode = "Dara Kayan",
            locationAr = "منطقة الصفا – السويس",
            gatesAr = listOf(
                "بوابة على طريق قسم عتاقة",
                "بوابة على طريق مصر إيران",
                "بوابة على شارع أحمد زويل"
            ),
            unitAreaAr = "مساحة جميع الوحدات: 90 م²",
            constructionStatusAr = "المشروع جاهز بالكامل: المباني واللاندسكيب والطرق مكتملة والوحدات متشطبة وجاهزة",
            taglineAr = "منطقة الصفا • مساحة 90 م² • متاح دور أخير فقط",
            status = CompoundStatus.AVAILABLE_LAST_FLOOR,
            imageRes = R.drawable.img_compound_kayan,
            descriptionAr = "يقع كمباوند دارة كيان في منطقة الصفا الحيوية بالسويس مع 3 بوابات رئيسية تسهل الوصول من جميع المحاور. المشروع جاهز بالكامل وجميع الوحدات متشطبة وجاهزة بمساحة موحدة 90 م²، ويتاح حالياً وحدة دور أخير فقط بنظام التقسيط المباشر أو التمويل العقاري.",
            facilities = listOf(
                CompoundFacility(
                    titleAr = "3 بوابات ومداخل رئيسية",
                    detailAr = "على طريق قسم عتاقة، طريق مصر إيران، وشارع أحمد زويل",
                    category = FacilityCategory.ROADS_GATES
                ),
                CompoundFacility(
                    titleAr = "وحدات متشطبة وجاهزة (90 م²)",
                    detailAr = "اكتمال كافة المباني والتشطيبات الداخلية مع توفر فيديو توضيحي للتشطيب",
                    category = FacilityCategory.FINISH_READY
                ),
                CompoundFacility(
                    titleAr = "لاندسكيب وطرق داخلية مكتملة",
                    detailAr = "مساحات خضراء منسقة وطرق داخلية مرصوفة وجاهزة بالكامل",
                    category = FacilityCategory.LANDSCAPE
                ),
                CompoundFacility(
                    titleAr = "أمن وإدارة متكاملة",
                    detailAr = "منظومة أمن وحراسة وإدارة قائمة بالفعل لضمان الخصوصية والراحة",
                    category = FacilityCategory.SECURITY
                ),
                CompoundFacility(
                    titleAr = "تمويل عقاري وتقسيط مرن",
                    detailAr = "حجز 10,000 ج.م، مقدم 15%، وتعاون مع 8 بنوك مصرية كبرى",
                    category = FacilityCategory.SALES_FINANCE
                )
            ),
            highlightsAr = listOf(
                "سعر الوحدة: من 847,000 إلى 880,000 جنيه (المتاح دور أخير بسعر 847,000 جنيه)",
                "قيمة حجز الوحدة: 10,000 جنيه فقط",
                "مقدم 15% (127,000 جنيه) وقسط ربع سنوي 36,000 جنيه حتى 5 سنوات",
                "مساحة موحدة لجميع الوحدات 90 م² بتشطيب كامل",
                "متاح التمويل العقاري بمقدم 15% ومدة سداد حسب موافقة البنك"
            ),
            unitOffer = UnitOffer(
                availabilityBadgeAr = "متاح دور أخير فقط",
                floorDescriptionAr = "الدور الأخير — مساحة 90 م² متشطبة وجاهزة",
                unitAreaSqm = 90,
                priceMinEgp = 847_000L,
                priceMaxEgp = 880_000L,
                bookingDepositEgp = 10_000L,
                downPaymentPercent = 15,
                downPaymentEgp = 127_000L,
                quarterlyInstallmentEgp = 36_000L,
                quartersCount = 20,
                durationYears = 5,
                mortgageAvailable = true,
                mortgageSummaryAr = "مقدم 15% ومدة السداد حسب موافقة البنك الممول (8 بنوك متعاونة)"
            )
        ),
        Compound(
            id = "hayat",
            nameAr = "كمباوند دارة حياة",
            englishCode = "Dara Hayat",
            locationAr = "شارع إبراهيم نافع – السويس",
            gatesAr = listOf(
                "مدخل رئيسي على شارع إبراهيم نافع",
                "موقع مركزي قريب من خدمات محافظة السويس"
            ),
            unitAreaAr = "مساحة جميع الوحدات: 90 م²",
            constructionStatusAr = "الكمباوند جاهز بالكامل والوحدات متشطبة وجاهزة",
            taglineAr = "غير متاح حالياً (المرحلة الأولى Sold Out) • يضم مقر مكتب المبيعات الرئيسي",
            status = CompoundStatus.SOLD_OUT,
            imageRes = R.drawable.img_compound_hayat,
            descriptionAr = "يُعد كمباوند دارة حياة إحدى أبرز مراحل مشروع دارة السويس السكني بشارع إبراهيم نافع، ويحتضن مقر مكتب مبيعات صندوق التنمية الحضرية (عمارة 145 – الدور الأول). الكمباوند جاهز بالكامل، والمرحلة الأولى منه مباعة بالكامل (Sold Out) وغير متاح للطرح حالياً.",
            facilities = listOf(
                CompoundFacility(
                    titleAr = "مقر مكتب المبيعات الرئيسي",
                    detailAr = "عمارة 145 – الدور الأول على شارع إبراهيم نافع في خدمتكم طوال اليوم",
                    category = FacilityCategory.SALES_FINANCE
                ),
                CompoundFacility(
                    titleAr = "مباني منتهية وتشطيب كامل",
                    detailAr = "وحدات سكنية متشطبة وجاهزة بالكامل وفق مواصفات صندوق التنمية الحضرية",
                    category = FacilityCategory.FINISH_READY
                ),
                CompoundFacility(
                    titleAr = "حدائق مركزية ولاندسكيب",
                    detailAr = "مساحات خضراء منسقة بين العمارات توفر تهوية وإضاءة طبيعية مميزة",
                    category = FacilityCategory.LANDSCAPE
                ),
                CompoundFacility(
                    titleAr = "أمن وطرق داخلية منظمة",
                    detailAr = "طرق داخلية مرصوفة ومنظومة أمن وحراسة وإدارة متكاملة",
                    category = FacilityCategory.SECURITY
                )
            ),
            highlightsAr = listOf(
                "الكمباوند غير متاح حالياً (المرحلة الأولى فقط Sold Out)",
                "يضم مكتب المبيعات الرئيسي: شارع إبراهيم نافع – عمارة 145 – الدور الأول",
                "مباني منتهية ووحدات متشطبة وجاهزة بمساحة 90 م²",
                "الكمباوند جاهز بالكامل بمرافق ولاندسكيب وطرق داخلية مكتملة"
            ),
            unitOffer = null
        ),
        Compound(
            id = "bustan",
            nameAr = "كمباوند دارة بستان",
            englishCode = "Dara Bustan",
            locationAr = "السويس – مشروع دارة السويس",
            gatesAr = listOf(
                "مداخل ومخارج منظمة مرتبطة بالمحاور الرئيسية بالسويس"
            ),
            unitAreaAr = "مساحة جميع الوحدات: 90 م²",
            constructionStatusAr = "الكمباوند جاهز بالكامل والوحدات متشطبة وجاهزة",
            taglineAr = "غير متاح حالياً (المرحلة الأولى Sold Out) • مجتمع حدائق هادئ",
            status = CompoundStatus.SOLD_OUT,
            imageRes = R.drawable.img_compound_bustan,
            descriptionAr = "يتميز كمباوند دارة بستان بطابعه العمراني الهادئ والمساحات الخضراء المصممة للراحة العائلية. الكمباوند جاهز بالكامل، والمرحلة الأولى منه مباعة بالكامل (Sold Out) وغير متاح للطرح حالياً.",
            facilities = listOf(
                CompoundFacility(
                    titleAr = "حدائق وممرات مشاة مظللة",
                    detailAr = "تصميم عمراني يراعي المسافات البينية والخصوصية وجلسات العائلات",
                    category = FacilityCategory.LANDSCAPE
                ),
                CompoundFacility(
                    titleAr = "تشطيب كامل وجاهزية المباني",
                    detailAr = "اكتمال كافة العمارات وتشطيب الوحدات بالكامل",
                    category = FacilityCategory.FINISH_READY
                ),
                CompoundFacility(
                    titleAr = "طرق داخلية وبنية تحتية مكتملة",
                    detailAr = "طرق داخلية مرصوفة وموقع عام منسق بالكامل",
                    category = FacilityCategory.ROADS_GATES
                ),
                CompoundFacility(
                    titleAr = "منظومة أمن وإدارة",
                    detailAr = "شركة أمن وإدارة متخصصة للحفاظ على جودة الحياة داخل الكمباوند",
                    category = FacilityCategory.SECURITY
                )
            ),
            highlightsAr = listOf(
                "الكمباوند غير متاح حالياً (المرحلة الأولى فقط Sold Out)",
                "مباني منتهية بالكامل ووحدات متشطبة وجاهزة",
                "مساحات خضراء وممرات آمنة للعائلات والأطفال",
                "الكمباوند جاهز بالكامل مع توفر المرافق والأمن والإدارة"
            ),
            unitOffer = null
        )
    )

    val contactChannels: List<ContactChannel> = listOf(
        ContactChannel(
            id = "hotline",
            titleAr = "الخط الساخن لصندوق التنمية الحضرية",
            subtitleAr = "الاستعلام العام، الحجز، ومتابعة التمويل العقاري",
            phoneNumber = HOTLINE_NUMBER,
            isHotline = true
        ),
        ContactChannel(
            id = "mobile",
            titleAr = "المبيعات المباشرة (أ/ محمد عبده)",
            subtitleAr = "مدير مبيعات صندوق التنمية الحضرية بمحافظة السويس • متاح لإرسال فيديو التشطيب",
            phoneNumber = SALES_MOBILE_NUMBER,
            isHotline = false
        )
    )

    val projectAdvantages: List<Pair<String, String>> = listOf(
        "مباني منتهية وتشطيب كامل" to "جميع المباني تم الانتهاء منها والوحدات بمساحة 90 م² متشطبة وجاهزة.",
        "حجز ميسر بـ 10,000 جنيه" to "قيمة حجز الوحدة 10,000 ج.م فقط مع مقدم 15% وتقسيط حتى 5 سنوات.",
        "8 بنوك للتمويل العقاري" to "بنك مصر، الأهلي، CIB، QNB، الإسكان والتعمير، القاهرة، تنمية الصادرات، وتنمية الصناعات.",
        "مكتب مبيعات داخل السويس" to "كمباوند دارة حياة – شارع إبراهيم نافع – عمارة 145 – الدور الأول (على مدار اليوم)."
    )

    fun getCompoundById(id: String): Compound? = compounds.find { it.id == id }
}
