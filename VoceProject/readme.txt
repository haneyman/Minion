

Project Instructions


cd to the directory containing the jar files, perform the following to store the jars in local maven repo:

mvn install:install-file -Dfile=cmu_us_kal.jar -DgroupId=com.sun.speech.freetts.en.us -DartifactId=cmu_us_kal -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=cmulex.jar -DgroupId=com.sun.speech.freetts.en.us -DartifactId=cmulex -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=en_us.jar -DgroupId=com.sun.speech.freetts.en -DartifactId=en_us -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=freetts.jar -DgroupId=com.sun.speech -DartifactId=freetts -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=jsapi.jar -DgroupId=javax.speech -DartifactId=jsapi -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=sphinx4.jar -DgroupId=edu.cmu.sphinx -DartifactId=sphinx4 -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz.jar -DgroupId=edu.cmu.sphinx.model.acoustic.WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz -DartifactId=WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=voce.jar -DgroupId=voce -DartifactId=voce -Dversion=1.0 -Dpackaging=jar
