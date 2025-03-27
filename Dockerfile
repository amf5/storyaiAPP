# استخدم JDK 17 (أو النسخة التي تعمل بها)
FROM eclipse-temurin:17-jdk

# تحديد المجلد داخل الحاوية
WORKDIR /app

# نسخ ملف JAR إلى الحاوية
COPY target/story_AI-0.0.1-SNAPSHOT.jar app.jar

# ستشغيل التطبيق عند بدء الحاوية
ENTRYPOINT ["java", "-jar", "app.jar"]
