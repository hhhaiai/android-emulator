# 为 Android 模拟器配置硬件加速


## 从命令行配置图形加速

如需在从命令行运行 AVD 时指定图形加速类型，请添加 -gpu 选项，如以下示例所示：

```
emulator -avd avd_name -gpu mode [{-option [value]} ... ]

```
mode 的值可以设为以下某个选项：

    auto：让模拟器根据计算机设置选择硬件图形加速或软件图形加速。
    host：使用计算机上的 GPU 实现硬件加速。通常，使用此选项时，模拟器的图形质量和性能最高。不过，如果您的图形驱动程序在渲染 OpenGL 时出现问题，那么您可能需要使用 swiftshader_indirect 或 angle_indirect 选项。
    swiftshader_indirect：使用与快速启动兼容的 SwiftShader 变体，利用软件加速渲染图形。如果您的计算机无法使用硬件加速，此选项可以有效地替代 host 模式。
    angle_indirect：（仅适用于 Windows）使用与快速启动兼容的 ANGLE Direct3D 变体，利用软件加速渲染图形。如果您的计算机无法使用硬件加速，此选项可以有效地替代 host 模式。 在大多数情况下，使用 ANGLE 时的性能应该与使用 host 模式时的性能差不多，因为 ANGLE 使用 Microsoft DirectX 而不是 OpenGL。在 Windows 上，Microsoft DirectX 驱动程序通常比 OpenGL 驱动程序的问题要少。此选项使用 Direct3D 11，并且要求运行 Windows 10、Windows 8.1 或带有 Windows 7 Platform Update 的 Windows 7 SP1。
    guest：使用访客端软件渲染。使用此选项时，模拟器的图形质量和性能最低。

* 注意：如果您启用计算机不支持的图形加速选项，则可能会在模拟期间看到显示的图像不正确。

* 以下 mode 选项已弃用：

    swiftshader：在版本 27.0.2 中已弃用，请改用 swiftshader_indirect。
    angle：在版本 27.0.2 中已弃用，请改用 angle_indirect（仅适用于 Windows）。
    mesa：在版本 25.3 中已弃用。请改用 swiftshader_indirect。


## 为 Android 界面启用 Skia 渲染
使用 API 级别 27 或更高级别的映像时，模拟器可以使用 Skia 渲染 Android 界面。Skia 可帮助模拟器更流畅、更高效地渲染图形。

如需启用 Skia 渲染，请在 adb shell 中使用以下命令：

```
su
setprop debug.hwui.renderer skiagl
stop
start
```

## 配置虚拟机加速

虚拟机加速使用计算机的处理器来显著提高模拟器的执行速度。一个称为 Hypervisor 的工具使用计算机处理器提供的虚拟化扩展来管理此交互。本部分概述了使用虚拟机加速的要求，并介绍了如何在各个操作系统上设置虚拟机加速。

### 常规要求
如需在模拟器中使用虚拟机加速，您的计算机必须满足本部分的常规要求。此外，您的计算机还需要满足特定于您的操作系统的其他要求。

### 开发环境要求
如需使用虚拟机加速，您的开发环境必须满足以下要求：

SDK 工具：最低为版本 17；建议使用版本 26.1.1 或更高版本
带有基于 x86 的系统映像的 AVD，适用于 Android 2.3.3（API 级别 10）及更高版本
警告：使用基于 ARM 或 MIPS 的系统映像的 AVD 无法使用本页介绍的虚拟机加速。
### 虚拟化扩展要求
除了满足开发环境要求之外，您的计算机处理器还必须支持以下虚拟化扩展技术之一：

Intel 虚拟化技术（VT、VT-x 和 vmx）扩展
AMD 虚拟化（AMD-V 和 SVM）扩展
大多数现代处理器都支持这些虚拟化扩展。如果您不确定自己的处理器是否支持这些扩展，请在制造商的网站上查看自己的处理器的规格。如果您的处理器不支持其中某项扩展，则您无法使用虚拟机加速。

注意：虚拟化扩展通常通过计算机 BIOS 启用，并且默认情况下往往处于关闭状态。如需了解如何启用虚拟化扩展，请查看主板的相关文档。
### 限制
虚拟机加速存在以下限制：

您无法在另一个虚拟机（例如由 VirtualBox、VMWare 或 Docker 托管的虚拟机）内运行虚拟机加速的模拟器。您必须直接在主机上运行虚拟机加速的模拟器。
根据您的操作系统和 Hypervisor，您无法在运行虚拟机加速的模拟器的同时运行使用其他虚拟化技术的软件。例如，VirtualBox、VMWare 和 Docker 当前使用其他虚拟化技术，因此您无法在运行加速的模拟器的同时运行它们。

### 关于 Hypervisor
虚拟机加速需要使用 Hypervisor。

如果没有 Hypervisor 和虚拟机加速，模拟器必须逐块转换虚拟机中的机器代码，以使其符合主机的架构。此过程可能很慢。有了 Hypervisor，虚拟机与主机的架构相符，因此模拟器可以使用 Hypervisor 直接在主机处理器上运行代码。这种改进可极大地提高模拟器的速度和性能。

最适合您的 Hypervisor 取决于计算机的操作系统和配置。如需了解详情，请参阅以下相应部分：

[在 Windows 上配置虚拟机加速](https://developer.android.com/studio/run/emulator-acceleration?hl=zh-cn#vm-windows)
[在 macOS 上配置虚拟机加速](https://developer.android.com/studio/run/emulator-acceleration?hl=zh-cn#vm-mac)
[在 Linux 上配置虚拟机加速](https://developer.android.com/studio/run/emulator-acceleration?hl=zh-cn#vm-linux)



检查是否安装了 Hypervisor
您可以使用模拟器的 -accel-check 命令行选项来检查计算机上当前是否安装了 Hypervisor。

以下示例展示了如何使用模拟器的 accel-check 选项。在每个示例中，Sdk 都是 Android SDK 的位置：

Windows：

```
c:\Users\janedoe\AppData\Local\Android> Sdk\emulator\emulator -accel-check
accel:
0
HAXM version 7.3.2 (4) is installed and usable.
accel
```

macOS：

```
janedoe-macbookpro:Android janedoe$ ./Sdk/emulator/emulator -accel-check
accel:
0
HAXM version 7.3.2 (4) is installed and usable.
accel
```

Linux:

```
janedoe:~/Android$ ./Sdk/emulator/emulator -accel-check
accel:
0
KVM (version 12) is installed and usable.
```


### 在 Windows 上配置虚拟机加速
Windows 上的虚拟机加速可以使用以下三个 Hypervisor 之一：Intel Hardware Accelerated Execution Manager (HAXM)、Android Emulator Hypervisor Driver for AMD Processors 或 Windows Hypervisor Platform (WHPX).

在 Windows 上选择 Hypervisor
您可以按照以下条件来确定应使用哪个 Hypervisor：

条件	Hypervisor
您拥有 Intel 处理器且不需要在运行 Android 模拟器的同时运行 Hyper-V。
    使用 Intel [HAXM](https://developer.android.com/studio/run/emulator-acceleration?hl=zh-cn#vm-windows-haxm-intel)。
您拥有 Intel 处理器且需要在运行 Android 模拟器的同时运行 Hyper-V。
    使用 [WHPX](https://developer.android.com/studio/run/emulator-acceleration?hl=zh-cn#vm-windows-whpx)。
您拥有 AMD 处理器且不需要在运行 Android 模拟器的同时运行 Hyper-V。
    使用 [Android Emulator Hypervisor Driver for AMD Processors](https://developer.android.com/studio/run/emulator-acceleration?hl=zh-cn#vm-windows-aehdamd)。
您拥有 AMD 处理器且需要在运行 Android 模拟器的同时运行 Hyper-V。
    使用 [WHPX](https://developer.android.com/studio/run/emulator-acceleration?hl=zh-cn#vm-windows-whpx。

### 停用 Hyper-V 时仔细检查
必须停用 Hyper-V 才能使用 Intel HAXM 或 Android Emulator Hypervisor Driver for AMD Processors。但是，在“Windows 功能”对话框中取消选中“Hyper-V”可能无法保证“Hyper-V”被停用。Windows 10 中有很多功能都会隐式启用 Hyper-V。用户在启用此类功能中的某一项功能时，甚至不会知道 Hyper-V 被启用。

据我们所知，此类功能包括 Virtual Machine Platform、Windows Hypervisor Platform、Windows Sandbox、Core Isolation、Credential Guard。此外，适用于 Linux 版本 2 的 Windows 子系统需要使用 VM Machine Platform，这意味着它隐式需要 Hyper-V。此列表并非详尽无遗，如果您发现应纳入此列表的功能，请通过我们的错误跟踪器通知我们。

停用 Hyper-V 时，请仔细确认上面列出的功能也处于停用状态。如需了解如何停用各项功能，请参阅 Microsoft 文档和以下示例。

![windows-features](https://raw.githubusercontent.com/hhhaiai/Picture/main/img/202202091853352.png)

![core-isolation](https://raw.githubusercontent.com/hhhaiai/Picture/main/img/202202091853427.png)



某些平台不会在您关闭 Hyper-V 时立即停用它。发生这种情况时，Windows 操作系统不会返回任何错误，且 Hyper-V 在“Windows 功能”对话框中为已停用状态。如果您遇到这种情况，请通过 Microsoft 的问题跟踪器提交错误。

### 在 Windows 上使用 Intel HAXM 配置虚拟机加速
您的计算机必须满足以下要求，您才能安装和使用 Intel HAXM：

* 启用了虚拟化技术 (VT-x)、Intel EM64T (Intel 64) 功能和 Execute Disable (XD) Bit 功能的 Intel 处理器
* 64 位 Windows 10、Windows 8 或 Windows 7（或者 64 位处理器上的 32 位版本的操作系统）
* 如需在 Windows 10 或 Windows 8 上使用 Intel HAXM，您必须在“Windows 功能”对话框中关闭 Hyper-V。
* 注意：安装某些软件可能会重新开启 Hyper-V。如需了解详情，请参阅停用 Hyper-V。

如需安装 Intel HAXM 驱动程序，请按以下步骤操作：

*  打开 SDK Manager。
* 点击 SDK Update Sites 标签，然后选择 Intel HAXM。
* 点击 OK。
* 下载完成后，运行安装程序。 通常，您可以在以下位置找到安装程序：sdk\extras\intel\Hardware_Accelerated_Execution_Manager\intelhaxm-android.exe
* 使用向导完成安装。
*  安装 Intel HAXM 后，通过在命令提示符窗口中输入以下命令，确认虚拟化驱动程序正常运行：
    ```
    sc query intelhaxm
    ```
    您应该会看到一条状态消息，其中包含以下信息：
    ```
    SERVICE_NAME: intelhaxm
    ...
    STATE              : 4  RUNNING
    ...
    ```
如需了解详情，请参阅 [Windows 上的 Intel HAXM 安装说明](https://github.com/intel/haxm/wiki/Installation-Instructions-on-Windows)。

您可以通过再次运行安装程序来调整可供 Intel HAXM 内核扩展使用的内存量。

您可以使用安装程序或 Windows 控制面板来卸载 Intel HAXM。在卸载 Intel HAXM 之前，请关闭当前正在运行的所有 x86 模拟器。

### 在 Windows 上使用 Android Emulator Hypervisor Driver for AMD Processors 配置虚拟机加速
您的计算机必须满足以下要求，您才能安装和使用 Android Emulator Hypervisor Driver for AMD Processors：

    采用 Secure Virtual Machine (SVM) 技术的 AMD 处理器
    64 位 Windows 10、Windows 8 或 Windows 7（32 位 Windows 不受支持）
    如需在 Windows 10 或 Windows 8 上使用 Android Emulator Hypervisor Driver for AMD Processors，您必须在“Windows 功能”对话框中关闭 Hyper-V。
        注意：安装某些软件可能会重新开启 Hyper-V。如需了解详情，请参阅停用 Hyper-V。
您可以通过 Android Studio 4.0 Canary 5 或更高版本中的 SDK 管理器 或通过 GitHub 安装 Android Emulator Hypervisor Driver for AMD Processors（请参阅下文）。如需通过 SDK 管理器安装，请按以下步骤操作：

    打开 Tools->SDK Manager。
    点击 SDK Tools 标签页，然后选择 Android Emulator Hypervisor Driver for AMD Processors。
    点击 OK，以下载并安装 Android Emulator Hypervisor Driver for AMD processors。
    安装后，通过在命令提示符窗口中输入以下命令，确认驱动程序正常运行：

    ```
    sc query gvm
    ```
    您应该会看到一条状态消息，其中包含以下信息：
    
    ```
    SERVICE_NAME: gvm
    ...
    STATE              : 4  RUNNING
    ...
    ```
    以下错误消息表示您的 BIOS 中未启用 SVM，或您未停用 Hyper-V（请参阅停用 Hyper-V）。
    
    ```
    SERVICE_NAME: gvm
    ...
    STATE              : 1  STOPPED
    WIN32_EXIT_CODE    : 4294967201 (0xffffffa1)
    ...
    ```
此外，您也可以通过 GitHub 下载并安装 Android Emulator Hypervisor Driver for AMD Processors。 解压缩驱动程序软件包后，在具有管理员权限的命令提示符中运行“silent_install.bat”。成功完成新安装后，您会看到以下输出
![aehd-install-new](https://raw.githubusercontent.com/hhhaiai/Picture/main/img/202202091857614.png)

如果是升级安装，则会看到以下输出，其中包括有关原有驱动程序卸载情况的消息：

![aehd-install-update](https://raw.githubusercontent.com/hhhaiai/Picture/main/img/202202091857809.png)


您可以通过在具有管理员权限的命令提示符中使用以下命令，卸载 Android Emulator Hypervisor Driver for AMD Processors。


sc stop gvm
sc delete gvm
注意：请先关闭所有 x86 模拟器，然后再卸载 Android Emulator Hypervisor Driver for AMD Processors。

使用 Windows Hypervisor Platform 配置虚拟机加速
您的计算机必须满足以下要求，您才能启用 WHPX：

Intel 处理器：支持虚拟化技术 (VT-x)、扩展页表 (EPT) 和无限制访客 (UG) 功能。必须在计算机的 BIOS 设置中启用 VT-x。
AMD 处理器：建议使用 AMD Ryzen 处理器。必须在计算机的 BIOS 设置中启用虚拟化或 SVM。
Android Studio 3.2 Beta 1 或更高版本（从 developer.android.com 下载）
Android 模拟器版本 27.3.8 或更高版本（使用 SDK Manager 下载）
Windows 10 April 2018 Update 或更高版本
如需在 Windows 上安装 WHPX，请按以下步骤操作：

在 Windows 桌面上，右键点击 Windows 图标，然后选择应用程序和功能。
在相关设置下，点击程序和功能。
点击打开或关闭 Windows 功能。
选中 Windows Hypervisor Platform。
点击 确定。

安装完成后，重启计算机。



### 在 macOS 上配置虚拟机加速
在 Mac OS X v10.10 Yosemite 及更高版本的操作系统上，Android 模拟器默认使用内置的 Hypervisor.Framework，如果 Hypervisor.Framework 未能初始化，则回退到使用 Intel HAXM。

如需在 Hypervisor.Framework 不可用时在 macOS 上使用虚拟机加速，您必须安装 Intel HAXM 内核扩展。

注意：对于 macOS 10.13 High Sierra 及更高版本的用户：macOS 10.13 默认停用内核扩展的安装。由于 Intel HAXM 是内核扩展，因此您可能需要手动启用其安装。如需了解详情，请参阅已知问题。
如需安装 Intel HAXM 内核扩展，请按以下步骤操作：

打开 SDK Manager。
点击 SDK Update Sites 标签，然后选择 Intel HAXM。
点击 OK。
下载完成后，运行安装程序。 通常，您可以在以下位置找到安装程序：sdk/extras/intel/Hardware_Accelerated_ExecutionManager/IntelHAXMversion.dmg
按照屏幕上的说明完成安装。
安装完成后，通过打开终端窗口并运行以下命令，确认新的内核扩展正常运行：


kextstat | grep intel
您应该会看到一条状态消息，其中包含以下扩展名称，表明已加载内核扩展：


com.intel.kext.intelhaxm
如需了解详情，请参阅 Intel HAXM 安装说明。

您可以通过再次运行安装程序来调整可供 Intel HAXM 内核扩展使用的内存量。

您可以通过卸载来停止使用 Intel HAXM 内核扩展。在卸载之前，请关闭当前正在运行的所有 x86 模拟器，然后在终端窗口中运行以下命令：


sudo /System/Library/Extensions/intelhaxm.kext/Contents/Resources/uninstall.sh

## 在 Linux 上配置虚拟机加速
基于 Linux 的系统通过 KVM 软件包来支持虚拟机加速。 请按照在 Linux 系统上安装 KVM 的说明进行操作，并验证是否启用了 KVM。对于 Ubuntu 系统，请参阅 Ubuntu KVM 安装。

要求
运行 KVM 需要特定的用户权限。请确保您具有 KVM 安装说明中指定的足够权限。

如需在 Linux 上使用虚拟机加速，您的计算机还必须满足以下要求：

对于 Intel 处理器：支持虚拟化技术 (VT-x)、Intel EM64T (Intel 64) 功能且启用了 Execute Disable (XD) Bit 功能。
对于 AMD 处理器：支持 AMD 虚拟化 (AMD-V)。
检查 Linux 上当前是否安装了 KVM
您可以使用模拟器的 -accel-check 命令行选项来检查是否安装了 KVM。或者，您也可以安装包含 kvm-ok 命令的 cpu-checker 软件包。

以下示例展示了如何使用 kvm-ok 命令。

安装 cpu-checker 软件包：


$ sudo apt-get install cpu-checker
$ egrep -c '(vmx|svm)' /proc/cpuinfo
输出值 1 或更大值表示支持虚拟化。输出值 0 表示您的 CPU 不支持硬件虚拟化。

运行命令 kvm-ok：


$ kvm-ok
预期输出： INFO: /dev/kvm exists KVM acceleration can be used

如果出现以下错误，则表示您仍然可以运行虚拟机。如果没有 KVM 扩展，您的虚拟机运行速度会减慢。 INFO: Your CPU does not support KVM extensions KVM acceleration can NOT be used

在 Linux 上安装 KVM
使用以下命令安装 KVM：

Cosmic (18.10) 或更高版本


sudo apt-get install qemu-kvm libvirt-daemon-system libvirt-clients bridge-utils
Lucid (10.04) 或更高版本


sudo apt-get install qemu-kvm libvirt-bin ubuntu-vm-builder bridge-utils
Karmic (9.10) 或更低版本


sudo aptitude install kvm libvirt-bin ubuntu-vm-builder bridge-utils

